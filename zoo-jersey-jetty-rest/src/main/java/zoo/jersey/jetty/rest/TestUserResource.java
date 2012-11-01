package zoo.jersey.jetty.rest;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/users/{username : [a-zA-Z][a-zA-Z_0-9]}")
@Named
public class TestUserResource {
	
	@GET
    public String getUser(@PathParam("username") String userName) {
		return userName;
    }
}
