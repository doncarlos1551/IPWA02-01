package de.iu.herotozero.herotozero_backend.resource;

import de.iu.herotozero.herotozero_backend.model.Benutzer;
import de.iu.herotozero.herotozero_backend.service.BenutzerService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/benutzer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
@RolesAllowed({"Admin"})
public class BenutzerResource {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(AuthResource.class.getName());
	
	@Inject
	JsonWebToken jwt;

    @Inject
    private BenutzerService benutzerService;

    @GET
    public Response getAllBenutzer() {
        List<Benutzer> benutzer = benutzerService.getAllBenutzer();
        return Response.ok(benutzer).build();
    }

    @GET
    @Path("/{id}")
    public Response getBenutzerById(@PathParam("id") Long id) {
        Benutzer benutzer = benutzerService.getBenutzer(id);
        if (benutzer != null) {
            return Response.ok(benutzer).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Benutzer zum Holen wurde nicht gefunden.").build();
        }
    }

    @POST
    public Response createBenutzer(Benutzer benutzer) {
        if (!benutzerService.isBenutzernameVerfügbar(benutzer.getBenutzername())) {
            return Response.status(Response.Status.CONFLICT).entity("Benutzername bereits vergeben.").build();
        }
        benutzerService.createBenutzer(benutzer);
        return Response.status(Response.Status.CREATED).entity(benutzer).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBenutzer(@PathParam("id") Long id, Benutzer updatedBenutzer) {
        if (!benutzerService.isBenutzernameVerfügbar(updatedBenutzer.getBenutzername())) {
            return Response.status(Response.Status.CONFLICT).entity("Benutzername bereits vergeben.").build();
        }
        Benutzer benutzer = benutzerService.updateBenutzer(id, updatedBenutzer);
        if (benutzer != null) {
            return Response.ok(benutzer).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Benutzer zum Updaten wurde nicht gefunden.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBenutzer(@PathParam("id") Long id) {
        if (benutzerService.deleteBenutzer(id)) {
        	return Response.status(Response.Status.OK).entity("Benutzer wurde gelöscht.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Benutzer zum Löschen wurde nicht gefunden.").build();
        }
    }
}