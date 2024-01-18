package de.iu.herotozero.herotozero_backend.resource;

import de.iu.herotozero.herotozero_backend.model.EmissionsDaten;
import de.iu.herotozero.herotozero_backend.service.EmissionsDatenService;
import de.iu.herotozero.herotozero_backend.util.StringResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/emissionsdaten")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class EmissionsDatenResource {
	
	@Inject
	JsonWebToken jwt;
	
    @Inject
    private EmissionsDatenService emissionsDatenService;

    @GET
    @PermitAll
    public Response getAllEmissionsDaten() {
        List<EmissionsDaten> emissionsDaten = emissionsDatenService.getAllEmissionsDaten();
        return Response.ok(emissionsDaten).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
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
    	if (!jwt.getGroups().contains("Admin")) {
    		emissionsDaten.setValidiert(false);
    	}
        emissionsDatenService.saveEmissionsDaten(emissionsDaten);
        return Response.status(Response.Status.CREATED).entity(emissionsDaten).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public Response updateEmissionsDaten(@PathParam("id") Long id, EmissionsDaten updatedEmissionsDaten) {
    	if (!jwt.getGroups().contains("Admin")) {
    		updatedEmissionsDaten.setValidiert(false);
    	}
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
            return Response.status(Response.Status.OK).entity(new StringResponse("Emissionsdaten wurden gelöscht.")).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @PUT
    @Path("/validieren/{id}")
    @RolesAllowed("Admin")
    public Response validateEmissionsDaten(@PathParam("id") Long id) {
        EmissionsDaten emissionsDaten = emissionsDatenService.validateEmissionsDaten(id);
        if (emissionsDaten != null) {
            return Response.ok(emissionsDaten).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}