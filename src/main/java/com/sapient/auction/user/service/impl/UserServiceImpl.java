package com.sapient.auction.user.service.impl;

import com.sapient.auction.user.entity.User;
import com.sapient.auction.user.repository.UserRepository;
import com.sapient.auction.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dpadal on 11/11/2016.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {
        log.info("Registering the user : {}", user.getId());
        return userRepository.save(user);
    }

    @Override
    public boolean login(User user) {
        log.info("Looking up for the user : {} in DB.", user.getId());
        User dbUser = userRepository.findOne(user.getId());
        return dbUser != null ? true : false;
    }
}
