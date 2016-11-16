package com.sapient.auction.sale.service;

import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;

import java.util.List;

/**
 * Created by dpadal on 11/14/2016.
 */
public interface SaleService {

    Sale create(Sale sale);

    Sale detail(long id);

    List<Sale> list();

    boolean bid(Bid bid);

    Bid getLatestBid(int saleId);
}
