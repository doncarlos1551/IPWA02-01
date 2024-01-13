package de.iu.herotozero.herotozero_backend.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/test")
public class TestResource {

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String getTestNachricht() {
        return "Das ist ein erster Test";
    }
    
    @Path("/secure-test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("Admin")
    public String getSecureTest() {
        return "Das ist ein erster Test der Admin-Rechte erfordert";
    }
}