package zoo.daroo.h2.mem;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.springframework.core.io.Resource;

import zoo.daroo.h2.mem.spring.JdbcConnectionPoolBean;

public class InternalDbManager {
	public final static String BEAN_ID = "InternalDbManager";
	
	@Inject
	@Named(JdbcConnectionPoolBean.BEAN_ID)
	private DataSource dataSource;
	
	private Resource initScriptLocation;

	public void initDatabase() {
		
	}
	
	
	public void setInitScriptLocation(Resource initScriptLocation) {
		this.initScriptLocation = initScriptLocation;
	}
}
