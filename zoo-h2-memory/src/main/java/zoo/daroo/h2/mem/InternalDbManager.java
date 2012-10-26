package zoo.daroo.h2.mem;

import javax.sql.DataSource;

class InternalDbManager {
	
	private final DataSource dataSource;
	
	InternalDbManager(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	

}
