package zoo.daroo.h2.mem;

import java.sql.Connection;

import org.h2.jdbcx.JdbcConnectionPool;

public class A {
    // http://www.h2database.com/html/tutorial.html#fulltext
    // FullTextLucene

    public static void main(String[] args) throws Exception {
	JdbcConnectionPool cp = JdbcConnectionPool.create("jdbc:h2:~/test", "sa", "sa");
	Connection conn = cp.getConnection();
	conn.close();
	cp.dispose();

    }
}
