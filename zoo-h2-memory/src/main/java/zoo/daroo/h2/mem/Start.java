package zoo.daroo.h2.mem;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
	
	public static void main(String[] args) {
		final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/spring/service-beans.xml");
		
		CountDownLatch latch = new CountDownLatch(1);
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		applicationContext.close();
	}

}
