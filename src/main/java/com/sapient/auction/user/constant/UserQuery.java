package com.sapient.auction.user.constant;

/**
 * Created by dpadal on 11/17/2016.
 */
public final class UserQuery {

    public static final String IS_USER_ID_ALREADY_EXIST = "SELECT count(ID) FROM USER WHERE ID = :userId";
    public static final String IS_USER_AUTHENTICATED = "SELECT user.* FROM USER user WHERE user.ID = :userId and user.PASSWORD = :password";
}
