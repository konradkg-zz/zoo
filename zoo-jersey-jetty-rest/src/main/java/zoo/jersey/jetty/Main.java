package zoo.jersey.jetty;

import org.eclipse.jetty.server.Server;


public class Main {
	
	public static void main(String[] args) throws Exception {

		System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
		final Server server = new Server(8080);
		
		server.start();
		while (System.in.available() == 0) {
			Thread.sleep(5000);
		}
		
		server.stop();
		server.join();
		
		
	}

}
