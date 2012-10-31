package zoo.jersey;

public class Main {

	public static void main(String[] args) throws Exception {

		System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
		// server.start();
		while (System.in.available() == 0) {
			Thread.sleep(5000);
		}
	}
}
