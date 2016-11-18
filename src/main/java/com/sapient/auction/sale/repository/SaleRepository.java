package com.sapient.auction.sale.repository;

import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository class, to perform all Sale related CRUD operations and lookup.
 *
 * Created by dpadal on 11/14/2016.
 */
public interface SaleRepository extends JpaRepository<Sale, Long> {
	Sale create(Sale sale);

	Sale detail(long id);

	List<Sale> list();

	boolean bid(Bid bid);

	Bid getLatestBid(int saleId);
}
