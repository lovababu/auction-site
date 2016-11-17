package com.sapient.auction.user.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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
    @GenericGenerator(strategy = "increment", name = "increment")
    @GeneratedValue(generator = "increment")
    private Long id;

    @Column(name = "DOOR_NUM", nullable = false)
    private String doorNumber;

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

    @Column(name = "TYPE", nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, referencedColumnName = "ID")
    private User user;
}
