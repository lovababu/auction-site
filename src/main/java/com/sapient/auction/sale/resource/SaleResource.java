package com.sapient.auction.sale.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by dpadal on 11/14/2016.
 */
@Path("/sale")
public class SaleResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create() {
        return Response.ok().entity("Sale created successfully").build();
    }

    @GET
    @Path("/{saleId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response detail() {
        return Response.ok().entity("Sale information fetched successfully").build();
    }

    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.ok().entity("Sale created successfully").build();
    }

    @POST
    @Path("/{saleId}/bid")
    public Response bid() {
        return Response.ok().entity("Bid posted successfully.").build();
    }

    @GET
    @Path("/{saleid}/bid")
    public Response latestBid() {
        return Response.ok().entity("Fetched latest bid successfully.").build();
    }

}