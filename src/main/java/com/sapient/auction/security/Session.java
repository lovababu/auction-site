package com.sapient.auction.security;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Session", indexes = {@Index(unique = true, columnList = "token")})
public class Session implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6128539360965809840L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "token")
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	private String token;

	@Column(name = "userId")
	private String userId;
	
	@Column(name = "expiry")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiry;
}
