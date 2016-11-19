package com.sapient.auction.sale.repository.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.sale.repository.SaleRepository;
import com.sapient.auction.sale.constant.SaleQuery;
import org.springframework.transaction.annotation.Transactional;

/**
 * Sale Repository implementation class. Both Sale and Bid database operations goes here.
 */
@Repository
@Slf4j
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
    public Sale detail(long id) {
        Query query = sessionFactory.getCurrentSession().createQuery(SaleQuery.SALE_ID);
        query.setLong("id", id);
        return (Sale) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Sale> list() {
        return (List<Sale>) sessionFactory.getCurrentSession().createQuery(SaleQuery.ALL_SALE).list();
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
