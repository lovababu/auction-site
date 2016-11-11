package com.sapient.auction.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by dpadal on 11/11/2016.
 */
@Entity
@Table(name = "ADDRESS")
@Setter @Getter
public class Address {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;

    @Column(name = "LANE1", nullable = false)
    private String lane1;

    @Column(name = "LANE2")
    private String lane2;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "STATE", nullable = false)
    private String state;

    @Column(name = "COUNTRY", nullable = false)
    private String country;

    @Column(name = "ZIPCODE", nullable = false)
    private String zipCode;
}
