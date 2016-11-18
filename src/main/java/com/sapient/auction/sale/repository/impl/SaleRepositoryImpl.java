package com.sapient.auction.sale.repository.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.sale.repository.SaleRepository;
import com.sapient.auction.sale.constant.SaleQuery;

@Repository
public class SaleRepositoryImpl implements SaleRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@org.springframework.transaction.annotation.Transactional
	public Sale create(Sale sale) {
		sale.setStartTime(LocalDateTime.now());
		sale.setEndTime(new Date());
		sessionFactory.getCurrentSession().save(sale);
		return sale;
	}

	@Override
	public Sale detail(long id) {
		Query query = sessionFactory.getCurrentSession().createQuery(SaleQuery.SALE_ID);
		query.setLong("id", id);
		Object queryResult = query.uniqueResult();
		Sale sale = (Sale) queryResult;
		sessionFactory.getCurrentSession().save(sale);
		return sale;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sale> list() {
		Query queryResult = sessionFactory.getCurrentSession().createQuery(SaleQuery.ALL_SALE);
		List<Sale> allSales;
		allSales = queryResult.list();
		return allSales;
	}

	@Override
	public boolean bid(Bid bid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Bid getLatestBid(int saleId) {
		// TODO Auto-generated method stub
		return null;
	}

}
