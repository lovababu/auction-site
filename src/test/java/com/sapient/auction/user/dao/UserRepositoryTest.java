package com.sapient.auction.user.dao;

import com.sapient.auction.SapAuctionSiteApplication;
import com.sapient.auction.common.config.DbConfig;
import com.sapient.auction.common.exception.SapAuctionException;
import com.sapient.auction.user.entity.Address;
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

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testGetUserByEmail() {
        User user = userRepository.register(user("dpadala@sapient.com"));
        assertNotNull(user.getId());
        Optional<User> userOptonal = userRepository.getUserByEmail("dpadala@sapient.com");
        assertTrue(userOptonal.isPresent());
    }

    private User user(String email) {
        User user = new User();
        user.setPassword("123456789");
        user.setFirstName("Durga");
        user.setLastName("Lovababu");
        user.setContact("8123717649");
        user.setEmail(email);
        Address address = new Address();
        address.setDoorNumber("1-25");
        address.setCity("Bangalore");
        address.setLane1("Munnekollala");
        address.setState("KA");
        address.setCountry("IN");
        address.setUser(user);
        user.setAddresses(new HashSet<Address>() {
            {
                add(address);
            }
        });

        return user;
    }

    @After
    public void tearDown() {
        userRepository = null;
    }
}
