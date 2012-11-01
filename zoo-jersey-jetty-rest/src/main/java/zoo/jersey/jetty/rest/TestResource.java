package zoo.jersey.jetty.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.yammer.metrics.annotation.Timed;

@Path("/")
public class TestResource {
	
	@GET
	@Timed
    public String get() {
        return "GET";
    }
}
