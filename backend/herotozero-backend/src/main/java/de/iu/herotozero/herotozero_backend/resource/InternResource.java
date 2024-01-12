package de.iu.herotozero.herotozero_backend.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/internal")
public class InternResource {

    @GET
    @Path("/publicKey")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPublicKey() {
        try {
            String publicKey = "  ";
            return Response.ok(publicKey).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fehler beim Laden des öffentlichen Schlüssels").build();
        }
    }
}
