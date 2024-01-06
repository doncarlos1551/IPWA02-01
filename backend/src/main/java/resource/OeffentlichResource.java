package resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/public")
public class OeffentlichResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getOeffentlicheDaten() {
        String data = "Test! Dies hier braucht keine Authenthifizierung.";
        return Response.ok(data).build();
    }
}