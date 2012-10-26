package zoo.daroo.h2.mem.spring;

import java.sql.SQLException;

import org.h2.tools.Server;

public class PostgreSqlServerBean extends AbstractServerBean {
	
	public final static String BEAN_ID = "PostgreSqlServerBean";

	@Override
	protected Server createServer(String... args) throws SQLException {
		return Server.createPgServer(args);
	}
	
	public void setAllowOthers(boolean allowOthers) {
		if(allowOthers) { 
			parameters.add("-pgAllowOthers");
		}
	}

}
