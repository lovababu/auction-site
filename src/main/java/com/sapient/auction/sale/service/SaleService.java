package com.sapient.auction.sale.service;

import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.sale.exception.SaleNotFoundException;

import java.util.List;

/**
 * Created by dpadal on 11/14/2016.
 */
public interface SaleService {

    Sale create(Sale sale);

    Sale detail(long id) throws SaleNotFoundException;

    List<Sale> list() throws SaleNotFoundException;

    boolean bid(Bid bid);

    Bid getLatestBid(int saleId);
}
