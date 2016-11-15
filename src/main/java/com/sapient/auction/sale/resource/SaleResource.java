package com.sapient.auction.sale.resource;

import com.sapient.auction.common.model.SaleVO;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Sale Module resource class.
 *
 * Created by dpadal on 11/14/2016.
 */
@Path("/sale")
@Slf4j
public class SaleResource {

    /**
     * Create brand new Sale/Auction.
     *
     * @return Response.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(SaleVO saleVO) {
        log.info("Processing the sale with userId: {}", saleVO.getUserVO().getId());
        return Response.ok().entity("Sale created successfully").build();
    }

    /**
     * Fetch sale details.
     *
     * @return Response.
     */
    @GET
    @Path("/{saleId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response detail() {
        return Response.ok().entity("Sale information fetched successfully").build();
    }

    /**
     * Return List of all Sales which are in Active state.
     *
     * @return Response
     */
    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.ok().entity("Sale created successfully").build();
    }

    /**
     * Bid for sale.
     *
     * @return Response
     */
    @POST
    @Path("/{saleId}/bid")
    public Response bid() {
        return Response.ok().entity("Bid posted successfully.").build();
    }

    /**
     * Retrieves the Latest Bid for the specified Sale.
     *
     * @return Response.
     */
    @GET
    @Path("/{saleid}/bid")
    public Response latestBid() {
        return Response.ok().entity("Fetched latest bid successfully.").build();
    }

}
