package zoo.daroo.h2.mem.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.tools.Server;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractServerBean implements InitializingBean, DisposableBean {

	private Server server;

	private boolean enable;
	protected List<String> parameters = new ArrayList<String>();

	protected Log logger = LogFactory.getLog(getClass());

	private String serverName = getClass().getSimpleName();

	private final AtomicBoolean Started = new AtomicBoolean(false);

	@Override
	public void afterPropertiesSet() throws Exception {
		if (enable) {
			if (Started.compareAndSet(false, true)) {
				server = createServer(parameters.toArray(new String[0]));
				logger.info("Starting " + serverName + " ...");
				server.start();
				logger.info("Server " + serverName + " listening on port: " + server.getPort());
			}
		} else {
			logger.info("Server " + serverName + " is disabled in configuration.");
		}
	}

	@Override
	public void destroy() throws Exception {
		if (Started.compareAndSet(true, false)) {
			if (server != null) {
				logger.info("Stopping " + serverName + " ...");
				server.stop();
				logger.info("Server " + serverName + " stopped.");
				server = null;
			}
		}
	}
	
	public String start(int port) {
		if (Started.compareAndSet(false, true)) {
			//setPort(port)
			//setAllowOther
			try {
				server = createServer(parameters.toArray(new String[0]));
				logger.info("[JMX] Starting " + serverName + " ...");
				server.start();
				logger.info("[JMX] Server " + serverName + " listening on port: " + server.getPort());
				return "Started on port: " + server.getPort();
			} catch (Exception e) {
				logger.error(e);
				return "Failed to start";
			}
		} else if (server != null) {
			return "Already started on port: " + server.getPort();
		} else {
			return "Error. Ambiguous state.";
		}
	}
	
	public void stop() {
		parameters.clear();
		try {
			destroy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	protected abstract Server createServer(String... args) throws SQLException;

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void setTrace(boolean trace) {
		if (trace) {
			parameters.add("-trace");
		}
	}
}