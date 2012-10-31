package zoo.jersey;

import org.glassfish.grizzly.http.server.HttpServer;

public class Main {

	public static void main(String[] args) throws Exception {

		System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
		final HttpServer httpServer = HttpServer.createSimpleServer("/test", 8080);
		httpServer.start();
		
		
		while (System.in.available() == 0) {
			Thread.sleep(5000);
		}
		
		httpServer.stop();
	}
}
