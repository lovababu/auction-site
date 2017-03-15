package com.sapient.auction.user.constant;

/**
 * Created by dpadal on 11/17/2016.
 */
public final class UserQuery {
    public static final String IS_USER_ID_ALREADY_EXIST = "SELECT count(ID) FROM USER WHERE email = :email";
    public static final String IS_AUTHENTICATED = "from User where email = :email and password = :password";
    public static final String USER_BY_EMAIL = "from User WHERE email = :email";
}
