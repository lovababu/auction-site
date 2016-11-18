package com.sapient.auction.sale.repository.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.sale.entity.Sale.Product;
import com.sapient.auction.sale.repository.SaleRepository;

@Repository
public class SaleRepositoryImpl implements SaleRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteInBatch(Iterable<Sale> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Sale> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sale> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sale> findAll(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public Sale getOne(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Sale> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Sale> S saveAndFlush(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Sale> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Long arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Sale arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Sale> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exists(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Sale findOne(Long arg0) {
		// TODO Auto-generated method stub

		String queryString = "from Sale where id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setLong("id", arg0);
		Object queryResult = query.uniqueResult();
		Sale sale = (Sale) queryResult;
		sessionFactory.getCurrentSession().save(sale);
		return sale;
	}

	@Override
	public <S extends Sale> S save(S arg0) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	@Transactional
	public Sale create(Sale sale) {
		Product product = sale.new Product();
		sale.setStartTime(LocalDateTime.now());
		sale.setEndTime(new Date());
		sale.setProduct(product);
		sessionFactory.getCurrentSession().save(sale);
		return sale;
	}

	@Override
	public Sale detail(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sale> list() {
		Query queryResult = sessionFactory.getCurrentSession().createQuery("from Sale");
		List<Sale> allSales;
		allSales = queryResult.list();
		for (int i = 0; i < allSales.size(); i++) {
			Sale sale = allSales.get(i);
		}
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
