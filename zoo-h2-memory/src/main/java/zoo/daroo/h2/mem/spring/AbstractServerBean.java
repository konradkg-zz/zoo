package zoo.daroo.h2.mem.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Server;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractServerBean implements InitializingBean, DisposableBean {
	
	private Server server;
	
	private boolean enable;
	protected List<String> parameters = new ArrayList<String>();
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(enable) {
			server = createServer(parameters.toArray(new String[0]));
			server.start();
		}
	}
	
	@Override
	public void destroy() throws Exception {
		if(server != null) {
			server.stop();
		}
	}

	protected abstract Server createServer(String... args) throws SQLException;
	
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public void setTrace(boolean trace) {
		if(trace) { 
			parameters.add("-trace");
		}
	}
}