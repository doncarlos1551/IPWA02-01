package de.iu.herotozero.herotozero_backend.resource;

import java.util.logging.Logger;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import de.iu.herotozero.herotozero_backend.util.KeyVerwaltung;
import java.security.PublicKey;
import java.util.Base64;

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

    @GET
    @Path("/public-key")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPublicKey() {
        try {
            PublicKey publicKey = KeyVerwaltung.getPublicKey();
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            return Response.ok(publicKeyString).build();
        } catch (Exception e) {
            logger.severe("Fehler beim Abrufen vom public key " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
