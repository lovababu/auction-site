package com.sapient.auction.sale.repository;

import com.sapient.auction.sale.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * Created by dpadal on 11/14/2016.
 */
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
