package de.iu.herotozero.herotozero_backend.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/test")
public class TestService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTestNachricht() {
        return "Das ist ein erster Test";
    }
}