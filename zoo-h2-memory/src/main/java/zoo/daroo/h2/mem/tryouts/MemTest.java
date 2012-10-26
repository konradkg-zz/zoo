package zoo.daroo.h2.mem.tryouts;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.h2.fulltext.FullTextLucene;
import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.jdbc.support.JdbcUtils;


public class MemTest {
    // http://www.h2database.com/html/tutorial.html#fulltext
    // FullTextLucene

    public static void main(String[] args) throws Exception {
	JdbcConnectionPool cp = JdbcConnectionPool.create("jdbc:h2:mem:test", "sa", "sa");
	Connection conn = cp.getConnection();
	conn.createStatement().executeUpdate("CREATE TABLE data ("
		   +" key VARCHAR(255) PRIMARY KEY,"
		   +" value VARCHAR(1023) )");
	String sql = 
	"CREATE ALIAS IF NOT EXISTS FTL_INIT FOR \"org.h2.fulltext.FullTextLucene.init\"; "
	+ "CALL FTL_INIT();";
	conn.createStatement().executeUpdate(sql);
	
	sql = "CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR); "
		+ "INSERT INTO TEST VALUES(1, 'Hello World');"
		+ "INSERT INTO TEST VALUES(2, 'Hello World1');"
		+ "CALL FTL_CREATE_INDEX('PUBLIC', 'TEST', NULL);";	
	
	conn.createStatement().executeUpdate(sql);
	
	sql = "SELECT * FROM FTL_SEARCH('Hello', 0, 0);";
	
	ResultSet rs = conn.createStatement().executeQuery(sql);
	while(rs.next()) {
	    System.out.println(JdbcUtils.getResultSetValue(rs, 1) + " --- " +  JdbcUtils.getResultSetValue(rs, 2));
	}
	
	
	System.out.println("FullTextLucene.search");
	rs = FullTextLucene.search(conn, "Hello", 0, 0);
	ResultSetMetaData rsMetaData = rs.getMetaData();
	int numberOfColumns = rsMetaData.getColumnCount();
	
	while(rs.next()) {
	    for(int i = 1; i <= numberOfColumns; i++) {
		System.out.print(JdbcUtils.getResultSetValue(rs, i) + " --- ");
	    }
	    System.out.println();
	}
	
	System.out.println("FullTextLucene.searchData");
	rs = FullTextLucene.searchData(conn, "Hello", 0, 0);
	rsMetaData = rs.getMetaData();
	numberOfColumns = rsMetaData.getColumnCount();
	
	while(rs.next()) {
	    for(int i = 1; i <= numberOfColumns; i++) {
		System.out.print(JdbcUtils.getResultSetValue(rs, i) + " --- ");
	    }
	    System.out.println();
	}
	
	conn.close();
	cp.dispose();

    }
}
