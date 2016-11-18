package com.sapient.auction.security;

import org.springframework.security.core.authority.AuthorityUtils;

import com.sapient.auction.user.entity.User;

public class CurrentSessionUser extends org.springframework.security.core.userdetails.User {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7539379440647176716L;
	private User user;
	
    public CurrentSessionUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getId() {
        return user.getId();
    }

//    public Role getRole() {
//        return user.getRole();
//    }
}