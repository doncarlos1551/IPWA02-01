package de.iu.herotozero.herotozero_backend.resource;

import de.iu.herotozero.herotozero_backend.util.KeyVerwaltung;
import de.iu.herotozero.herotozero_backend.util.StringResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.security.PublicKey;
import java.util.Base64;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/public")
public class OeffentlichResource {

    @Inject
	JsonWebToken jwt;

    @GET
    @Path("/public-key")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPublicKey() {
        try {
            PublicKey publicKey = KeyVerwaltung.getPublicKey();
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            return Response.ok(publicKeyString).build();
        } catch (Exception e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new StringResponse("Beim holen vom Public Key ist etwas komplett schief gelaufen.")).build();
        }
    }
}
