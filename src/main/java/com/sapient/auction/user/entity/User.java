package com.sapient.auction.user.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by dpadal on 11/11/2016.
 */
@Entity
@Table(name = "USER")
@Setter @Getter
public class User implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2708646471073046443L;

	@Id
    @Column(name = "ID")
    @GenericGenerator(strategy = "UUID", name = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "CONTACT", nullable = false)
    private String contact;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "ISACTIVE")
    private boolean isActive = true;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Address> addresses;
}