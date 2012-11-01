package zoo.jersey.jetty.rest.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.WebApplication;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.spi.spring.container.SpringComponentProviderFactory;

public class SpringServlet extends ServletContainer implements ApplicationContextAware {
	private static final long serialVersionUID = 2621190083306191596L;
	
	private ApplicationContext applicationContext;

	@Override
	protected void initiate(ResourceConfig rc, WebApplication wa) {
		wa.initiate(rc, new SpringComponentProviderFactory(rc, (ConfigurableApplicationContext) applicationContext));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
