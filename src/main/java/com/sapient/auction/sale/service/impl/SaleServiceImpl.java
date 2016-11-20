package com.sapient.auction.sale.service.impl;

import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.sale.exception.SaleNotFoundException;
import com.sapient.auction.sale.repository.SaleRepository;
import com.sapient.auction.sale.service.SaleService;
import com.sapient.auction.user.entity.User;
import com.sapient.auction.user.exception.UserNotFoundException;
import com.sapient.auction.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Sale create(Sale sale) throws UserNotFoundException {
		sale.setStartTime(new Date());
		//TODO: refactor once the security integrated.
		Optional<User> user = userRepository.getUserByEmail(sale.getUser().getEmail());
		if (user.isPresent()) {
			sale.setUser(user.get());
			saleRepository.create(sale);
			log.info("Sale record saved in db with id: {}", sale.getId());
		} else {
			throw new UserNotFoundException("Supplied UserNotFound.");
		}
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
		//to avoid lazy initialization exception.
		sale.getUser().getEmail();
		for (Bid bid : sale.getBids()) {
			bid.getUser().getEmail();
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
		//To Avoid lazy initialization exception.
		for (Sale sale : sales) {
			sale.getUser().getEmail();
			for (Bid bid: sale.getBids()) {
				bid.getUser().getEmail();
			}
		}
		return sales;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean bid(Bid bid) throws UserNotFoundException {
		bid.setTime(new Date());
		//TODO: refactor once the security integrated.
		Optional<User> user = userRepository.getUserByEmail(bid.getUser().getEmail());
		if (user.isPresent()) {
			bid.setUser(user.get());
			saleRepository.bid(bid);
			log.info("Bid record saved in db with id: {}", bid.getId());
			return true;
		} else {
			throw new UserNotFoundException("Supplied UserNotFound.");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Bid getLatestBid(Long saleId) {
		return saleRepository.getLatestBid(saleId);
	}
}
