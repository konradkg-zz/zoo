package zoo.daroo.h2.mem;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
	
	public static void main(String[] args) {
		final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/spring/service-beans.xml");
		
		
		applicationContext.close();
	}

}
