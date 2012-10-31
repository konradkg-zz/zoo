package zoo.jersey.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.xml.XmlConfiguration;

public class JettyMainXml {
	
	public static void main(String[] args) throws Exception {

		System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
		Server server = new Server();
		XmlConfiguration configuration = new XmlConfiguration(JettyMainXml.class.getResourceAsStream("jetty.xml"));
		configuration.configure(server);
		server.start();
		

		server.start();
		while (System.in.available() == 0) {
			Thread.sleep(5000);
		}
		
		server.stop();
		server.join();
		
		
	}

}
