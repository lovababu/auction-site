package com.sapient.auction.sale.service.impl;

import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;
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
import java.util.List;

/**
 * SaleService implementation class, responsible for create/get Sale info and bid.
 *
 * Created by dpadal on 11/14/2016.
 */
@Service
@Slf4j
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleRepository saleRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Sale create(Sale sale) {
		sale.setStartTime(new Date());
		saleRepository.create(sale);
		log.info("Sale record saved in db with id: {}", sale.getId());
		return sale;
	}

	@Override
	@Transactional(readOnly = true)
	public Sale detail(long id) throws SaleNotFoundException {
		log.info("Fetching the sale id: {}", id);
		Sale sale = saleRepository.detail(id);
		if (sale == null) {
			throw new SaleNotFoundException("Sale not found .");
		}
		return sale;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Sale> list() throws SaleNotFoundException {
		List<Sale> sales = saleRepository.list();
		if (CollectionUtils.isEmpty(sales)) {
			throw new SaleNotFoundException("Sales not found.");
		}
		log.info("Fetched no of sales: {}", sales.size());
		return sales;
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
