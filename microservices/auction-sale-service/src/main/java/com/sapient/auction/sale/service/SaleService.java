package com.sapient.auction.sale.service;

import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.sale.exception.BidNotAllowedException;
import com.sapient.auction.sale.exception.InvalidBidAmountException;
import com.sapient.auction.sale.exception.SaleNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dpadal on 11/14/2016.
 */
public interface SaleService {

    Sale create(Sale sale);

    Sale detail(String id) throws SaleNotFoundException;

    List<Sale> list() throws SaleNotFoundException;

    Bid bid(String saleId, Bid bid) throws SaleNotFoundException, InvalidBidAmountException, BidNotAllowedException;

    Bid getLatestBid(String saleId) throws SaleNotFoundException;
}
