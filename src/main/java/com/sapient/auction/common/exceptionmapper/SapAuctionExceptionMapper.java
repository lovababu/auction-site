package com.sapient.auction.common.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Globally Handling exceptions thrown by application and respond back to the user with error message and status code.
 *
 * Created by dpadal on 11/11/2016.
 */
public class SapAuctionExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        return null;
    }
}
