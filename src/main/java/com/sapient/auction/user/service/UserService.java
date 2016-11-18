package com.sapient.auction.user.service;

import com.sapient.auction.user.entity.User;
import com.sapient.auction.user.exception.UserAlreadyExistException;
import com.sapient.auction.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by dpadal on 11/11/2016.
 */

public interface UserService {

    User register(User user);

    boolean login(User user);
}
