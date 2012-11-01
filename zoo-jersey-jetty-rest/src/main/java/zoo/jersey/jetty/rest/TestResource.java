package zoo.jersey.jetty.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class TestResource {
	
	@GET
    public String get() {
        return "GET";
    }
}
