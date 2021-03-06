package com.sapient.auction.sale.service.impl;

import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.sale.exception.BidNotAllowedException;
import com.sapient.auction.sale.exception.InvalidBidAmountException;
import com.sapient.auction.sale.exception.SaleNotFoundException;
import com.sapient.auction.sale.repository.SaleRepository;
import com.sapient.auction.sale.service.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * SaleService implementation class, responsible for create/get Sale info and bid.
 * <p>
 * Created by dpadal on 11/14/2016.
 */
@Service
@Slf4j
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    /**
     * Create new Sale.
     * Set the sale time to current time.
     *
     * @param sale
     * @return
     * @throws UserNotFoundException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Sale create(Sale sale) {
        sale.setStartTime(new Date());
        saleRepository.create(sale);
        log.info("Sale record saved in db with id: {}", sale.getId());
        return sale;
    }

    /**
     * Returns the Sale info for specified sale id.
     * throws <code>SaleNotFoundException</code> if sale not exist.
     *
     * @param id
     * @return
     * @throws SaleNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public Sale detail(String id) throws SaleNotFoundException {
        log.info("Fetching the sale id: {}", id);
        Sale sale = saleRepository.detail(id);
        if (sale == null) {
            throw new SaleNotFoundException("Sale not found .");
        }
        sale.getBids();
        //to avoid lazy initialization exception.
        return sale;
    }

    /**
     * Returns List sales.
     * Throws <code>SaleNotFoundException</code> if zero sales.
     *
     * @return
     * @throws SaleNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public List<Sale> list() throws SaleNotFoundException {
        List<Sale> sales = saleRepository.list();
        if (CollectionUtils.isEmpty(sales)) {
            throw new SaleNotFoundException("Sales not found.");
        }
        log.info("Fetched no of sales: {}", sales.size());
        //To Avoid lazy initialization exception.
        for (Sale sale : sales) {
            
            Optional<Bid> highestBid = Optional.ofNullable(sale.getBids().stream()
                    .max((o1, o2) -> o1.getPrice().compareTo(o2.getPrice())).orElse(null));
            if (highestBid.isPresent()) {
                sale.setBids(new HashSet<Bid>() {
                    {
                        add(highestBid.get());
                    }
                });
            }

        }
        return sales;
    }

    /**
     * Post bid on sale.
     * Throws <code>SaleNotFoundException</code>, when the requested sale id not exist.
     * Throws <code>InvalidBidAmountException</code>, if the Bid price is less than existing bid for the sale.
     *
     * @param saleId
     * @param bid
     * @return
     * @throws SaleNotFoundException
     * @throws InvalidBidAmountException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Bid bid(String saleId, Bid bid) throws SaleNotFoundException, InvalidBidAmountException, BidNotAllowedException {
        Sale sale = saleRepository.detail(saleId);
        if (sale == null) {
            throw new SaleNotFoundException("Sale not found.");
        }  else {
            boolean isBidPriceLess = false;
            if (!CollectionUtils.isEmpty(sale.getBids())) {
                isBidPriceLess = sale.getBids().stream().anyMatch(existingBid ->
                        (existingBid.getPrice().compareTo(bid.getPrice()) >= 0));
            }

            if (!isBidPriceLess) {
                bid.setTime(new Date());
                bid.setSale(sale);
                saleRepository.bid(bid);
                log.info("Bid record saved in db with id: {}", bid.getId());
            } else {
                throw new InvalidBidAmountException("Bid price is Invalid.");
            }
        }
        return bid;
    }

    /**
     * Get the Latest Bid.
     * Returns the bid which is higher price.
     * Throws <code>SaleNotFoundException</code>, when the sale id not exist.
     *
     * @param saleId
     * @return
     * @throws SaleNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public Bid getLatestBid(String saleId) throws SaleNotFoundException {
        if (!saleRepository.isSaleExist(saleId)) {
            throw new SaleNotFoundException("Sale not found.");
        } else {
            Bid bid = saleRepository.getLatestBid(saleId);
            bid.getSale().getProductId();
            return bid;
        }
    }
}
