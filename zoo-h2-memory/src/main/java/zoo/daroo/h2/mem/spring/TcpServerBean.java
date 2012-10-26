package zoo.daroo.h2.mem.spring;

import java.sql.SQLException;

import org.h2.tools.Server;

public class TcpServerBean extends AbstractServerBean {

	@Override
	protected Server createServer(String... args) throws SQLException {
		return Server.createTcpServer(args);
	}

}
