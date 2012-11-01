package zoo.jersey.jetty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class SpringJettyServer implements InitializingBean, DisposableBean {
	private final Log logger = LogFactory.getLog(getClass());
	
	private final Server server = new Server();
	private Connector[] connectors;
	private Handler handler;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		server.setConnectors(connectors);
		server.setHandler(handler);
		try {
			server.start();
		} catch (Exception e) {
			destroy();
			
			throw e;
		}
	}
	
	@Override
	public void destroy() throws Exception {
		try {
			server.stop();
			server.join();
		} catch (Exception e) {
			logger.error("Jetty server destroy method failed.", e);
		}
		
	}
	
	 public void setConnectors(Connector[] connectors) {
		 this.connectors = connectors;
	 }
	 
	 public void setHandler(Handler handler) {
		 this.handler = handler;
	 }

}
