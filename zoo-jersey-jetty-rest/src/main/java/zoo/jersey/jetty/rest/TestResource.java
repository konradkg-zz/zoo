package zoo.jersey.jetty.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.yammer.metrics.annotation.Timed;

@Path("/")
public class TestResource {
	
	@GET
	@Timed
	//@Metered
    public String get() {
        return "GET";
    }
	//@Metered and @ExceptionMetered 
}
