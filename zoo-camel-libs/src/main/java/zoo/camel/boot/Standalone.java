package zoo.camel.boot;

import org.apache.camel.spring.Main;

public class Standalone {
    
    private Main main;
    
    public static void main(String[] args) throws Exception {
	Standalone standalone = new Standalone();
	standalone.boot();
    }
    
    public void boot() throws Exception {
	main = new Main();
	main.enableHangupSupport();
	main.setApplicationContextUri("classpath:META-INF/spring/camel-context.xml");
	System.out.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
        main.run();
    }
    
    
}
