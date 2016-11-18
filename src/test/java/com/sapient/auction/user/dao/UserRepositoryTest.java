package com.sapient.auction.user.dao;

import com.sapient.auction.SapAuctionSiteApplication;
import com.sapient.auction.common.config.DbConfig;
import com.sapient.auction.common.exception.SapAuctionException;
import com.sapient.auction.user.entity.User;
import com.sapient.auction.user.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;

/**
 * Created by dpadal on 11/18/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SapAuctionSiteApplication.class)
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void before() {

    }

    @Test
    public void testUserRepository() {
        assertNotNull(userRepository);
    }

    @Test
    public void testCreateUser() {
        userRepository.register(new User());
    }

    @After
    public void tearDown() {
        userRepository = null;
    }
}
