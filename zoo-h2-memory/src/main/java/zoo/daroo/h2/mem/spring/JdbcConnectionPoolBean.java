package zoo.daroo.h2.mem.spring;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class JdbcConnectionPoolBean implements DataSource, InitializingBean, DisposableBean {
	
	public final static String BEAN_ID = "JdbcConnectionPoolBean";

	private JdbcConnectionPool jdbcConnectionPool;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//"jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
		this.jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:mem:pex_online", "sa2", "");
	}
	
	@Override
	public void destroy() throws Exception {
		if(jdbcConnectionPool != null) {
			jdbcConnectionPool.dispose();
		}
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return jdbcConnectionPool.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		jdbcConnectionPool.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		jdbcConnectionPool.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return jdbcConnectionPool.getLoginTimeout();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new RuntimeException("Not supported method");
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new RuntimeException("Not supported method");
	}

	@Override
	public Connection getConnection() throws SQLException {
		return jdbcConnectionPool.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return jdbcConnectionPool.getConnection(username, password);
	}

	//JDK1.7
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new RuntimeException("Not supported method");
	}
}
