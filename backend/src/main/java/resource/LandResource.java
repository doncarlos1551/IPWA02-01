package resource;

import model.Land;
import service.LandService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;

@Path("/laender")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class LandResource {

    @Inject
    private LandService landService;

    @GET
    public Response getAllLaender() {
        List<Land> laender = landService.getAllLaender();
        return Response.ok(laender).build();
    }

    @GET
    @Path("/{id}")
    public Response getLandById(@PathParam("id") Long id) {
        Land land = landService.getLand(id);
        if (land != null) {
            return Response.ok(land).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @RolesAllowed({"Admin", "User"})
    public Response createLand(Land land) {
        landService.createLand(land);
        return Response.status(Response.Status.CREATED).entity(land).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public Response updateLand(@PathParam("id") Long id, Land updatedLand) {
        Land land = landService.updateLand(id, updatedLand);
        if (land != null) {
            return Response.ok(land).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Admin")
    public Response deleteLand(@PathParam("id") Long id) {
        if (landService.deleteLand(id)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
