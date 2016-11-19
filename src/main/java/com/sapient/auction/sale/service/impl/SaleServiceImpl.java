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
		return saleRepository.create(sale);
	}

	@Override
	public Sale detail(long id) {
		return saleRepository.detail(id);
	}

	@Override
	public List<Sale> list() {
		return saleRepository.list();
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
