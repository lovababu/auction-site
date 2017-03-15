package com.sapient.auction.sale.resource;

import com.sapient.auction.sale.exception.AuthenticationFailedException;
import com.sapient.auction.sale.exception.SapAuctionException;
import com.sapient.auction.sale.api.AuctionResponse;
import com.sapient.auction.sale.api.BidVO;
import com.sapient.auction.sale.api.SaleVO;
import com.sapient.auction.sale.security.AuthenticationHelper;
import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.sale.exception.BidNotAllowedException;
import com.sapient.auction.sale.exception.InvalidBidAmountException;
import com.sapient.auction.sale.exception.SaleNotFoundException;
import com.sapient.auction.sale.service.SaleService;
import com.sapient.auction.sale.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.awt.*;
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
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class SaleResource {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private SaleService saleService;

    /**
     * Create brand new Sale/Auction.
     * Request must have "Authorization" header with base64 encoded value (email:<hashed password>).
     *
     * @return Response.
     * @throws SapAuctionException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid SaleVO saleVO) throws SapAuctionException, URISyntaxException {
        Sale saleEntity;
        try {
            saleEntity = ObjectMapperUtil.saleEntity(saleVO);
            saleService.create(saleEntity);
        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new SapAuctionException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unable to process the request, please try again.");
        }
        return Response.created(new URI(uriInfo.getAbsolutePath() + "/" + saleEntity.getId())).entity(
                AuctionResponse.builder().withStatusCode(Response.Status.CREATED.getStatusCode())
                        .withMessage(String.format("Sale %s created successfuly.", saleEntity.getId()))
                        .withSaleVO(SaleVO.builder().withId(saleEntity.getId())
                                .withProductId(saleEntity.getProductId()).build())
                        .build()
        ).build();
    }

    /**
     * Returns the requested Sale details.
     * Request must have "Authorization" header with base64 encoded value (email:<hashed password>).
     *
     * @return Response.
     * @throws SapAuctionException
     */
    @GET
    @Path("/{saleId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true)
    public Response detail(@PathParam("saleId") String saleId) throws SapAuctionException {
        log.info("Processing Sale detail request for Id: {}", saleId);
        Sale saleEntity;
        try {
            saleEntity = saleService.detail(saleId);
        } catch (SaleNotFoundException se) {
            log.error("Sale not found", se);
            throw new SapAuctionException(Response.Status.NOT_FOUND.getStatusCode(), se.getMessage());
        }catch (Exception e) {
            log.error("Exception: ", e);
            throw new SapAuctionException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unable to process the request, please try again.");
        }
        return Response.ok().entity(
                AuctionResponse.builder().withStatusCode(Response.Status.OK.getStatusCode())
                        .withSaleVO(ObjectMapperUtil.saleVO(saleEntity)).build()
        ).build();
    }

    /**
     * Return List of all Sales which are in active.
     * Request must have "Authorization" header with base64 encoded value (email:<hashed password>).
     *
     * @return Response
     * @throws SapAuctionException
     */
    @GET
    @Path("/list")
    @Transactional(readOnly = true)
    public Response list() throws SapAuctionException {
        List<Sale> sales;
        try {
            sales = saleService.list();
        } catch (SaleNotFoundException se) {
            log.error("Sale not found: ", se);
            throw new SapAuctionException(Response.Status.NOT_FOUND.getStatusCode(), se.getMessage());
        }catch (Exception e) {
            log.error("Exception: ", e);
            throw new SapAuctionException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unable to process the request, please try again.");
        }
        return Response.ok().entity(
                AuctionResponse.builder().withStatusCode(Response.Status.OK.getStatusCode())
                        .withSaleVO(sales.stream().map(sale
                                -> ObjectMapperUtil.saleVO(sale)).collect(Collectors.toSet())).build()
        ).build();
    }

    /**
     * Bid for sale.
     * Request must have "Authorization" header with base64 encoded value (email:<hashed password>).
     *
     * @return Response
     * @throws SapAuctionException
     */
    @POST
    @Path("/{saleId}/bid")
    @Transactional(propagation = Propagation.REQUIRED)
    public Response bid(@PathParam("saleId") String saleId, @Valid BidVO bidVO) throws SapAuctionException, URISyntaxException {
        Bid bidEntity;
        try {
            bidEntity = ObjectMapperUtil.bidEntity(bidVO);
            bidEntity = saleService.bid(saleId, bidEntity);
        } catch (SaleNotFoundException se) {
            log.error("Sale not found: ", se);
            throw new SapAuctionException(Response.Status.BAD_REQUEST.getStatusCode(), se.getMessage());
        } catch (BidNotAllowedException se) {
            log.error("Bid Not Allowed. ", se);
            throw new SapAuctionException(Response.Status.BAD_REQUEST.getStatusCode(), se.getMessage());
        } catch (InvalidBidAmountException ie) {
            log.error("Not a valid bid amount: ", ie);
            throw new SapAuctionException(Response.Status.BAD_REQUEST.getStatusCode(), ie.getMessage());
        } catch (Exception ex) {
            log.error("Exception: ", ex);
            throw new SapAuctionException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unable to process the request, please try again.");
        }
        return Response.created(new URI(
                uriInfo.getAbsolutePath() + "/sale/" + bidEntity.getSale().getId() + "/bid/" + bidEntity.getId()))
                .entity(AuctionResponse.builder().withStatusCode(Response.Status.CREATED.getStatusCode())
                        .withMessage("Bid posted successfully.")
                        .withBidVO(BidVO.builder().withId(bidEntity.getId()).build())
                        .build())
                .build();
    }

    /**
     * Retrieves the Latest Bid for the specified Sale.
     * Request must have "Authorization" header with base64 encoded value (email:<hashed password>).
     *
     * @return Response.
     * @throws SapAuctionException
     */
    @GET
    @Path("/{saleId}/bid")
    public Response latestBid(@PathParam("saleId") String saleId) throws SapAuctionException {
        Bid bid;
        try {
            bid = saleService.getLatestBid(saleId);
        } catch (Exception ex) {
            log.error("Exception: ", ex);
            throw new SapAuctionException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unable to process the request, please try again.");
        }
        return Response.ok().entity(
                AuctionResponse.builder().withStatusCode(Response.Status.OK.getStatusCode())
                        .withBidVO(ObjectMapperUtil.bidVO(bid)).build()
        ).build();
    }

}
