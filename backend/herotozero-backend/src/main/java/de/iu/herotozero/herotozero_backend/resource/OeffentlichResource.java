package de.iu.herotozero.herotozero_backend.resource;

import java.util.logging.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/public")
public class OeffentlichResource {
	private static final Logger logger = Logger.getLogger(OeffentlichResource.class.getName());
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getOeffentlicheDaten() {
    	logger.info("getOeffentlicheDaten Methode aufgerufen");
        String data = "Test! Dies hier braucht keine Authenthifizierung.";
        return Response.ok(data).build();
    }
}