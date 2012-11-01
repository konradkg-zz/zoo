package zoo.jersey.jetty;

import java.util.logging.LogManager;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringJettyMain {
	
	public static void main(String[] args) throws Exception {
		
		//System.getProperty("java.util.logging.config.file");
		LogManager.getLogManager().readConfiguration(SpringJettyMain.class.getResourceAsStream("/logging.properties"));
		
		ClassPathXmlApplicationContext applicationContext = null;
		try {
			applicationContext = new ClassPathXmlApplicationContext("spring-jetty.xml");
		
			while (System.in.available() == 0) {
				Thread.sleep(1000);
			}
		
		} finally {
			//Metrics.shutdown();
			
			if(applicationContext != null) {
				applicationContext.destroy();
			}
		}
		
		
		
		
		
	}

}
