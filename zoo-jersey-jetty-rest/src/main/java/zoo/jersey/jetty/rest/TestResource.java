package zoo.jersey.jetty.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import zoo.jersey.jetty.rest.beans.SimpleBean;

import com.yammer.metrics.annotation.Timed;

@Path("/")
public class TestResource {
	
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
