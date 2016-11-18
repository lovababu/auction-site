package com.sapient.auction.sale.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Entity class mapped to SALE data table.
 *
 * Created by dpadal on 11/14/2016.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SALE")
@Setter
@Getter
public class Sale implements Serializable {

	@Id
	private Long id;

	@Column(name = "START_DATE", nullable = false)
	private LocalDateTime startTime;

	@Column(name = "END_DATE", nullable = false)
	private Date endTime;

	@Column(name = "PRICE", nullable = false)
	private BigDecimal price;

	@Embedded
	private Product product;

	@Setter
	@Getter
	@Embeddable
	public class Product {

		@Column(name = "PRODUCT_ID", nullable = false)
		private String id;

		@Column(name = "NAME", nullable = false)
		private String name;

		@Column(name = "TYPE", nullable = false)
		private String type;

		@Column(name = "DESC")
		private String desc;
	}
}
