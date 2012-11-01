package zoo.jersey.jetty.rest.beans;

import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@Named("SimpleBean")
public class SimpleBean implements InitializingBean, DisposableBean {
	private final Log logger = LogFactory.getLog(getClass()); 

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("INIT");
	}
	
	@Override
	public void destroy() throws Exception {
		logger.info("DESTROY");
		
	}
}
