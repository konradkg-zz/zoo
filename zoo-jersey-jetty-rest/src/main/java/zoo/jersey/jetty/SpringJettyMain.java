package zoo.jersey.jetty;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringJettyMain {
	
	public static void main(String[] args) throws Exception {
		
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-jetty.xml");
		
		while (System.in.available() == 0) {
			Thread.sleep(5000);
		}
		
		
		
		applicationContext.destroy();
		
	}

}
