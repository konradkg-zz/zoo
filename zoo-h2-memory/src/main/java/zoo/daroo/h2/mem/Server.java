package zoo.daroo.h2.mem;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Server implements InitializingBean, DisposableBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO init mem connection
		
		// create in-memory structures
		
	}
	
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
