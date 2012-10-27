package zoo.daroo.h2.mem;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import zoo.daroo.h2.mem.spring.JdbcConnectionPoolBean;

public class InternalDbManager {
	public final static String BEAN_ID = "InternalDbManager";
	private final static Log Logger = LogFactory.getLog(InternalDbManager.class);
	
	@Inject
	@Named(JdbcConnectionPoolBean.BEAN_ID)
	private DataSource dataSource;
	
	private Resource initScriptLocation;
	private Resource createFtlIndexScriptLocation;
	private Resource createPexTempTableScriptLocation;
	private Resource switchTablesScriptLocation;

	
	public void initDatabase() throws IOException, SQLException {
		final String sql = readFile(initScriptLocation);
		executeSql(sql, "initDatabase");
	}
	
	public void createFtlIndex() throws IOException, SQLException {
		final String sql = readFile(createFtlIndexScriptLocation);
		executeSql(sql, "createFtlIndex");
	}
	
	public void createPexTempTable() throws IOException, SQLException {
		final String sql = readFile(createPexTempTableScriptLocation);
		executeSql(sql, "createPexTempTable");
	}
	
	public void switchTables() throws IOException, SQLException {
		final String sql = readFile(switchTablesScriptLocation);
		executeSql(sql, "switchTables");
	}
	
	private String readFile(Resource resource) throws IOException {
		final InputStream is = new BufferedInputStream(resource.getInputStream());
		byte[] buffer = new byte[1024];
		final StringBuilder str = new StringBuilder();
		try {
			while(is.read(buffer) != -1) {
				str.append(new String(buffer, "UTF-8"));
				buffer = new byte[1024];
			}
		} finally {
			is.close();
		}
		
		return str.toString();
	}
	
	private void executeSql(String sql, String description) throws SQLException {
		final long start = System.nanoTime();
		Connection con = null;
		try {
			con = dataSource.getConnection();
			if(Logger.isDebugEnabled()) {
				Logger.debug("Executing sql: " + sql);
			}
			con.createStatement().execute(sql);
		} finally {
			if(con != null) {
				con.close();
			}
			if(description != null) {
				Logger.info(description + " execution took " 
					+ TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS) + " [ms]");
			}
		}
	}
	
	
	public void setInitScriptLocation(Resource initScriptLocation) {
		this.initScriptLocation = initScriptLocation;
	}
	
	public void setCreateFtlIndexScriptLocation(Resource createFtlIndexScriptLocation) {
		this.createFtlIndexScriptLocation = createFtlIndexScriptLocation;
	}

	public void setCreatePexTempTableScriptLocation(Resource createPexTempTableScriptLocation) {
		this.createPexTempTableScriptLocation = createPexTempTableScriptLocation;
	}

	public void setSwitchTablesScriptLocation(Resource switchTablesScriptLocation) {
		this.switchTablesScriptLocation = switchTablesScriptLocation;
	}
}
