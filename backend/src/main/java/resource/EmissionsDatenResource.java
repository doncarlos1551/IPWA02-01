package resource;

import model.EmissionsDaten;
import service.EmissionsDatenService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;

@Path("/emissionsdaten")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class EmissionsDatenResource {

    @Inject
    private EmissionsDatenService emissionsDatenService;

    @GET
    public Response getAllEmissionsDaten() {
        List<EmissionsDaten> emissionsDaten = emissionsDatenService.getAllEmissionsDaten();
        return Response.ok(emissionsDaten).build();
    }

    @GET
    @Path("/{id}")
    public Response getEmissionsDatenById(@PathParam("id") Long id) {
        EmissionsDaten emissionsDaten = emissionsDatenService.getEmissionsDaten(id);
        if (emissionsDaten != null) {
            return Response.ok(emissionsDaten).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @RolesAllowed({"Admin", "User"})
    public Response createEmissionsDaten(EmissionsDaten emissionsDaten) {
        emissionsDatenService.saveEmissionsDaten(emissionsDaten);
        return Response.status(Response.Status.CREATED).entity(emissionsDaten).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public Response updateEmissionsDaten(@PathParam("id") Long id, EmissionsDaten updatedEmissionsDaten) {
        EmissionsDaten emissionsDaten = emissionsDatenService.updateEmissionsDaten(id, updatedEmissionsDaten);
        if (emissionsDaten != null) {
            return Response.ok(emissionsDaten).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Admin")
    public Response deleteEmissionsDaten(@PathParam("id") Long id) {
        if (emissionsDatenService.deleteEmissionsDaten(id)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
