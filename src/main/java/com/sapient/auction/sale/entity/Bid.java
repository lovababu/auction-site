package com.sapient.auction.sale.entity;

import com.sapient.auction.user.entity.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Bid entity class, mapped to BID table.
 *
 * Created by dpadal on 11/16/2016.
 */
@Entity
@Table(name = "BID")
public class Bid {

    @Id
    @Column(name = "ID")
    @GenericGenerator(strategy = "increment", name = "increment")
    @GeneratedValue(generator = "increment")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALE_ID", nullable = false, referencedColumnName = "ID")
    private Sale sale;
}
