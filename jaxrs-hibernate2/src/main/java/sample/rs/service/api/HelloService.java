package sample.rs.service.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

@Path("/account")
@Service
public interface HelloService {

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    String sayHello(@PathParam("name") String a);

}
