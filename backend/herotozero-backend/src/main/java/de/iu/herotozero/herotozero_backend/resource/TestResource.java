package de.iu.herotozero.herotozero_backend.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/test")
public class TestResource {
	
	@Inject
	JsonWebToken jwt;

	@Path("/public-test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
	@PermitAll
    public String getTestNachricht() {
        return "Das ist ein erster Test der von Jeden aufgerufen werden kann.";
    }
    
    @Path("/private-test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("Admin")
    public String getSecureTest() {
        return "Das ist ein erster Test der Admin-Rechte erfordert.";
    }
}