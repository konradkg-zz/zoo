package zoo.jersey.jetty.rest;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import zoo.jersey.jetty.rest.jaxb.SimpleResponse;

import com.yammer.metrics.annotation.Timed;

@Path("/jaxb")
@Named
public class TestJaxbResource {
	
	@GET
	@Timed
    public SimpleResponse get() {
		SimpleResponse response = new SimpleResponse();
		response.setText("SADDDDDDDDDD");
        return response;
    }
	
	@GET
	@Path("1")
	@Timed
    public Response get1() {
		SimpleResponse response = new SimpleResponse();
		response.setText("111");
        return Response.ok(response).build();
    }


}
