package com.sapient.auction.user.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User REST resource class.
 * Is responsible for create/update/delete/get User information.
 *
 * Created by dpadal on 11/10/2016.
 */

@Path(value = "/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register() {
        return Response.status(Response.Status.OK).entity("Registration successfull.").build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login() {
        return Response.status(Response.Status.OK).entity("Logged in successfull.").build();
    }
}
