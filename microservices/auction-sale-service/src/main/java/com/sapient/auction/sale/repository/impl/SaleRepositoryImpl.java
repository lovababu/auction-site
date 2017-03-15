package com.sapient.auction.sale.repository.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sapient.auction.sale.constant.SaleQuery;
import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.sale.repository.SaleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Sale Repository implementation class. Both Sale and Bid database operations goes here.
 */
@Repository
public class SaleRepositoryImpl implements SaleRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Sale create(Sale sale) {
        sessionFactory.getCurrentSession().save(sale);
        return sale;
    }

    @Override
    public Sale detail(String id) {
        Query query = sessionFactory.getCurrentSession().createQuery(SaleQuery.SALE_ID);
        query.setString("id", id);
        return (Sale) query.uniqueResult();
    }

    @Override
    public boolean isSaleExist(String id) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(SaleQuery.IS_SALE_EXIST);
        query.setString("saleId", id);
        BigInteger count = (BigInteger) query.uniqueResult();
        return count.intValue() > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Sale> list() {
        return (List<Sale>) sessionFactory.getCurrentSession().createQuery(SaleQuery.ALL_SALE).list();
    }

    @Override
    public synchronized Bid bid(Bid bid) {
        sessionFactory.getCurrentSession().save(bid);
        return bid;
    }

    @Override
    public Bid getLatestBid(String saleId) {
        Query query = sessionFactory.getCurrentSession().createQuery(SaleQuery.LATEST_BID);
        query.setString("saleId", saleId);
        return (Bid) query.uniqueResult();
    }
}
