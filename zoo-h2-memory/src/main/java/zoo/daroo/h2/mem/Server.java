package zoo.daroo.h2.mem;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import zoo.daroo.h2.mem.spring.JdbcConnectionPoolBean;

public class Server implements InitializingBean, DisposableBean {

	@Inject
	@Named(JdbcConnectionPoolBean.BEAN_ID)
	private DataSource internalDataSource;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		internalDataSource.getConnection();
		// TODO init mem connection
		
		// create in-memory structures
		
	}
	
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
