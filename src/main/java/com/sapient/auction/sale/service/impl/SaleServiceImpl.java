package com.sapient.auction.sale.service.impl;

import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.sale.repository.SaleRepository;
import com.sapient.auction.sale.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by dpadal on 11/14/2016.
 */
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public Sale create(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public Sale detail(long id) {
        return saleRepository.findOne(id);
    }

    @Override
    public List<Sale> list() {
        return saleRepository.findAll();
    }

    @Override
    public boolean bid(Bid bid) {
        return false;
    }

    @Override
    public Bid getLatestBid(int saleId) {
        return null;
    }
}
