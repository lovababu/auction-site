package com.sapient.auction.sale.dao;

import com.sapient.auction.SapAuctionSiteApplication;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.sale.repository.SaleRepository;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Lovababu on 11/19/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SapAuctionSiteApplication.class)
@Transactional
public class SaleRepositoryTest {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void before() {

    }

    @Test
    public void testIsSaleRepositoryNull() {
        assertNotNull(saleRepository);
    }

    @Test
    public void testCreateSale() {
        User user = userRepository.register(user());
        assertNotNull(user.getId());
        Sale sale = sale();
        sale.setUser(user);
        final Sale finalSale = sale;
        user.setSales(new HashSet<Sale>(){
            {
                add(finalSale);
            }
        });

        sale = saleRepository.create(sale);
        assertNotNull(sale.getId());

        Sale dbSale = saleRepository.detail(sale.getId());
        assertNotNull(dbSale);
        assertEquals(sale.getProductId(), dbSale.getProductId());

        List<Sale> sales = saleRepository.list();
        assertNotNull(sales);
        assertTrue(sales.size() > 0);
    }

    private User user() {
        User user = new User();
        user.setPassword("123456789");
        user.setFirstName("Durga");
        user.setLastName("Lovababu");
        user.setContact("8123717649");
        user.setEmail("dpadala@sapient.com");
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

    private Sale sale() {
        Sale sale = new Sale();
        sale.setStartTime(new Date());
        sale.setEndTime(new Date());
        sale.setProductId("Lenovo-3245");
        sale.setProductName("Lenovo  tab");
        sale.setProductDesc("Lenovo tablet.");
        sale.setPrice(new BigDecimal(15000));
        sale.setProductType("Electronic");

        return sale;
    }

    @After
    public void tearDown() {

    }
}
