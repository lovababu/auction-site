package com.sapient.auction.sale.exception;

/**
 * Created by dpadal on 11/24/2016.
 */
public class BidNotAllowedException extends Exception {
    private int statusCode;
    private String message;

    public BidNotAllowedException() {
        super();
    }

    public BidNotAllowedException(String message) {
        super(message);
        this.message = message;
    }

    public BidNotAllowedException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public BidNotAllowedException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
