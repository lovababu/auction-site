package com.sapient.auction.sale.resource;

import com.sapient.auction.common.exception.SapAuctionException;
import com.sapient.auction.common.model.AuctionResponse;
import com.sapient.auction.common.model.BidVO;
import com.sapient.auction.common.model.SaleVO;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.sale.exception.SaleNotFoundException;
import com.sapient.auction.sale.service.SaleService;
import com.sapient.auction.sale.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sale resource class.
 * <p>
 * Created by dpadal on 11/14/2016.
 */
@Path("/sale")
@Slf4j
public class SaleResource {

    @Autowired
    private UriInfo uriInfo;

    @Autowired
    private SaleService saleService;

    /**
     * Create brand new Sale/Auction.
     *
     * @return Response.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(SaleVO saleVO) throws SapAuctionException, URISyntaxException {
        log.info("Processing the sale with userId: {}", saleVO.getUserVO().getId());
        Sale saleEntity;
        try {
            saleEntity = saleService.create(ObjectMapperUtil.saleEntity(saleVO));
        } catch (Exception e) {
            throw new SapAuctionException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unable process the request, please try again.");
        }
        return Response.created(new URI(uriInfo.getAbsolutePath() + "/" + saleEntity.getId())).entity(
                AuctionResponse.builder().withStatusCode(Response.Status.CREATED.getStatusCode())
                        .withMessage(String.format("Sale %d created successfuly.", saleEntity.getId()))
                        .build()
        ).build();
    }

    /**
     * Fetch sale details.
     *
     * @return Response.
     */
    @GET
    @Path("/{saleId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response detail(@PathParam("saleId") long saleId) throws SapAuctionException {
        log.info("Processing Sale detail request for Id: {}", saleId);
        Sale saleEntity;
        try {
            saleEntity = saleService.detail(saleId);
        } catch (SaleNotFoundException se) {
            throw new SapAuctionException(Response.Status.NOT_FOUND.getStatusCode(), se.getMessage());
        } catch (Exception e) {
            throw new SapAuctionException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unable process the request, please try again.");
        }
        return Response.ok().entity(
                AuctionResponse.builder().withStatusCode(Response.Status.OK.getStatusCode())
                        .withSaleVO(ObjectMapperUtil.saleVO(saleEntity)).build()
        ).build();
    }

    /**
     * Return List of all Sales which are in Active state.
     *
     * @return Response
     */
    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response list() throws SapAuctionException {
        List<Sale> sales;
        try {
            sales = saleService.list();
        } catch (SaleNotFoundException se) {
            throw new SapAuctionException(Response.Status.NOT_FOUND.getStatusCode(), se.getMessage());
        } catch (Exception e) {
            throw new SapAuctionException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unable process the request, please try again.");
        }
        return Response.ok().entity(
                AuctionResponse.builder().withStatusCode(Response.Status.OK.getStatusCode())
                        .withSaleVO(sales.stream().map(sale
                                -> ObjectMapperUtil.saleVO(sale)).collect(Collectors.toSet()))
        ).build();
    }

    /**
     * Bid for sale.
     *
     * @return Response
     */
    @POST
    @Path("/{saleId}/bid")
    public Response bid(BidVO bidVO) {
        return Response.ok().entity("Bid posted successfully.").build();
    }

    /**
     * Retrieves the Latest Bid for the specified Sale.
     *
     * @return Response.
     */
    @GET
    @Path("/{saleid}/bid")
    public Response latestBid(@PathParam("saleId") long saleId) {
        return Response.ok().entity("Fetched latest bid successfully.").build();
    }

}
