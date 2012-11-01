package zoo.jersey.jetty.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import zoo.jersey.jetty.rest.beans.SimpleBean;

import com.yammer.metrics.annotation.Timed;

@Path("/b")
@Named
public class TestResource implements InitializingBean, DisposableBean {
	private final Log logger = LogFactory.getLog(getClass()); 

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("INIT");
	}
	
	@Override
	public void destroy() throws Exception {
		logger.info("DESTROY");
		
	}
	
	@Inject
	@Named("SimpleBean")
	private SimpleBean bean;
	
	@GET
	@Timed
    public String get() {
		bean.toString();
        return "GET";
    }
	
}
