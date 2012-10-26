package zoo.daroo.h2.mem.tryouts;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.concurrent.CountDownLatch;

import org.h2.fulltext.FullTextLucene;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.springframework.jdbc.support.JdbcUtils;

public class TCPServer {
	//jdbc:h2:mem:test;DB_CLOSE_DELAY=-1
	//jdbc:h2:tcp://localhost/mem:test
	public static void main(String[] args) throws Exception {
		mem();
		Server tcpServer = Server.createTcpServer(new String[] {});
		tcpServer.start();

		Server webServer = Server.createWebServer(new String[] {});
		webServer.start();

		//mem();
		
		final CountDownLatch latch = new CountDownLatch(1);
		latch.await();
		tcpServer.stop();
		webServer.stop();
	}

	public static void mem() throws Exception {
		JdbcConnectionPool cp = JdbcConnectionPool.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
		Connection conn = cp.getConnection();
		conn.createStatement().executeUpdate("CREATE TABLE data ("
				+ " key VARCHAR(255) PRIMARY KEY,"
				+ " value VARCHAR(1023) )");
		String sql =
				"CREATE ALIAS IF NOT EXISTS FTL_INIT FOR \"org.h2.fulltext.FullTextLucene.init\"; "
						+ "CALL FTL_INIT();";
		conn.createStatement().executeUpdate(sql);

		sql = "CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR); "
				+ "INSERT INTO TEST VALUES(1, 'Hello World');"
				+ "INSERT INTO TEST VALUES(2, 'Hello World1');"
				+ "INSERT INTO TEST VALUES(3, 'Dupa');"
				+ "CALL FTL_CREATE_INDEX('PUBLIC', 'TEST', NULL);";

		conn.createStatement().executeUpdate(sql);

		sql = "SELECT * FROM FTL_SEARCH('Hello', 0, 0);";

		ResultSet rs = conn.createStatement().executeQuery(sql);
		while (rs.next()) {
			System.out.println(JdbcUtils.getResultSetValue(rs, 1) + " --- " + JdbcUtils.getResultSetValue(rs, 2));
		}

		System.out.println("FullTextLucene.search");
		rs = FullTextLucene.search(conn, "Hello", 0, 0);
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int numberOfColumns = rsMetaData.getColumnCount();

		while (rs.next()) {
			for (int i = 1; i <= numberOfColumns; i++) {
				System.out.print(JdbcUtils.getResultSetValue(rs, i) + " --- ");
			}
			System.out.println();
		}

		System.out.println("FullTextLucene.searchData");
		rs = FullTextLucene.searchData(conn, "Hello", 0, 0);
		rsMetaData = rs.getMetaData();
		numberOfColumns = rsMetaData.getColumnCount();

		while (rs.next()) {
			for (int i = 1; i <= numberOfColumns; i++) {
				System.out.print(JdbcUtils.getResultSetValue(rs, i) + " --- ");
			}
			System.out.println();
		}

		conn.close();
		cp.dispose();

	}
	
	/**
	 * SELECT * FROM FTL_SEARCH('Hello', 0, 0);
SELECT * FROM FTL_SEARCH_DATA('Hello', 0, 0) FT 
JOIN TEST T ON T.ID = FT.KEYS[0]
	 */

	/**
	 * When running without options, -tcp, -web, -browser and -pg are started. <br />
	 * Options are case sensitive. Supported options are:
	 * <table>
	 * <tr>
	 * <td>[-help] or [-?]</td>
	 * <td>Print the list of options</td>
	 * </tr>
	 * <tr>
	 * <td>[-web]</td>
	 * <td>Start the web server with the H2 Console</td>
	 * </tr>
	 * <tr>
	 * <td>[-webAllowOthers]</td>
	 * <td>Allow other computers to connect - see below</td>
	 * </tr>
	 * <tr>
	 * <td>[-webDaemon]</td>
	 * <td>Use a daemon thread</td>
	 * </tr>
	 * <tr>
	 * <td>[-webPort &lt;port&gt;]</td>
	 * <td>The port (default: 8082)</td>
	 * </tr>
	 * <tr>
	 * <td>[-webSSL]</td>
	 * <td>Use encrypted (HTTPS) connections</td>
	 * </tr>
	 * <tr>
	 * <td>[-browser]</td>
	 * <td>Start a browser connecting to the web server</td>
	 * </tr>
	 * <tr>
	 * <td>[-tcp]</td>
	 * <td>Start the TCP server</td>
	 * </tr>
	 * <tr>
	 * <td>[-tcpAllowOthers]</td>
	 * <td>Allow other computers to connect - see below</td>
	 * </tr>
	 * <tr>
	 * <td>[-tcpDaemon]</td>
	 * <td>Use a daemon thread</td>
	 * </tr>
	 * <tr>
	 * <td>[-tcpPort &lt;port&gt;]</td>
	 * <td>The port (default: 9092)</td>
	 * </tr>
	 * <tr>
	 * <td>[-tcpSSL]</td>
	 * <td>Use encrypted (SSL) connections</td>
	 * </tr>
	 * <tr>
	 * <td>[-tcpPassword &lt;pwd&gt;]</td>
	 * <td>The password for shutting down a TCP server</td>
	 * </tr>
	 * <tr>
	 * <td>[-tcpShutdown "&lt;url&gt;"]</td>
	 * <td>Stop the TCP server; example: tcp://localhost</td>
	 * </tr>
	 * <tr>
	 * <td>[-tcpShutdownForce]</td>
	 * <td>Do not wait until all connections are closed</td>
	 * </tr>
	 * <tr>
	 * <td>[-pg]</td>
	 * <td>Start the PG server</td>
	 * </tr>
	 * <tr>
	 * <td>[-pgAllowOthers]</td>
	 * <td>Allow other computers to connect - see below</td>
	 * </tr>
	 * <tr>
	 * <td>[-pgDaemon]</td>
	 * <td>Use a daemon thread</td>
	 * </tr>
	 * <tr>
	 * <td>[-pgPort &lt;port&gt;]</td>
	 * <td>The port (default: 5435)</td>
	 * </tr>
	 * <tr>
	 * <td>[-properties "&lt;dir&gt;"]</td>
	 * <td>Server properties (default: ~, disable: null)</td>
	 * </tr>
	 * <tr>
	 * <td>[-baseDir &lt;dir&gt;]</td>
	 * <td>The base directory for H2 databases (all servers)</td>
	 * </tr>
	 * <tr>
	 * <td>[-ifExists]</td>
	 * <td>Only existing databases may be opened (all servers)</td>
	 * </tr>
	 * <tr>
	 * <td>[-trace]</td>
	 * <td>Print additional trace information (all servers)</td>
	 * </tr>
	 * </table>
	 * The options -xAllowOthers are potentially risky. <br />
	 * For details, see Advanced Topics / Protection against Remote Access.
	 * 
	 * @h2.resource
	 * 
	 */
	public void help() {

	}

}
