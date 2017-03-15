package com.sapient.auction.sale.config;

import com.sapient.auction.sale.exception.exceptionmapper.SapAuctionExceptionMapper;
import com.sapient.auction.sale.resource.SaleResource;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Jersey resource configuration class.
 *
 * Created by dpadal on 11/11/2016.
 */
@ApplicationPath("/auction")
public class RestConfig extends ResourceConfig{

    public RestConfig() {
        registerClasses(SaleResource.class, SapAuctionExceptionMapper.class);
    }
}
