package resource;

import model.Benutzer;
import service.BenutzerService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;

@Path("/benutzer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class BenutzerResource {

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
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @RolesAllowed({"Admin", "User"})
    public Response createBenutzer(Benutzer benutzer) {
        benutzerService.createBenutzer(benutzer);
        return Response.status(Response.Status.CREATED).entity(benutzer).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public Response updateBenutzer(@PathParam("id") Long id, Benutzer updatedBenutzer) {
        Benutzer benutzer = benutzerService.updateBenutzer(id, updatedBenutzer);
        if (benutzer != null) {
            return Response.ok(benutzer).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Admin")
    public Response deleteBenutzer(@PathParam("id") Long id) {
        if (benutzerService.deleteBenutzer(id)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
