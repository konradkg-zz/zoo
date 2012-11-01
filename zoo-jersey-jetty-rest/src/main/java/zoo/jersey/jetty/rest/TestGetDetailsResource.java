package zoo.jersey.jetty.rest;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.yammer.metrics.annotation.Timed;

//@Path("test/{id: d+}")
@Path("/a/{id}")
@Named
public class TestGetDetailsResource {
	
	@GET
	@Timed
    public String get(@PathParam("id") int id) {
        return "GET: " + id ;
    }

}
