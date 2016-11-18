package com.sapient.auction.e2e;

import com.sapient.auction.SapAuctionSiteApplication;
import com.sapient.auction.common.model.AuctionResponse;
import com.sapient.auction.common.model.ErrorVO;
import com.sapient.auction.user.entity.Address;
import com.sapient.auction.user.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by dpadal on 11/18/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SapAuctionSiteApplication.class)
@WebIntegrationTest(randomPort = true)
public class UserE2ETests {

    private RestTemplate restTemplate = null;

    @Value("${local.server.port}")  //boot injects the port.
    private int port;

    private String baseURL = null;


    private HttpHeaders httpHeaders;

    @Before
    public void setUp(){
        restTemplate = new TestRestTemplate();
        baseURL = "http://localhost:"+ port + "/auction/user";
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON);
    }


    /**
     * Test User Registration.
     * Expected: success 201 Created.
     */
    @Test
    public void testRegister() {
        String userId = "user" + System.currentTimeMillis();
        User user = user(userId);

        HttpEntity httpEntity = new HttpEntity(user, httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(baseURL, HttpMethod.POST, httpEntity, AuctionResponse.class);
        AuctionResponse response = (AuctionResponse) responseEntity.getBody();
        assertEquals(response.getStatusCode(), Response.Status.CREATED.getStatusCode());
        assertEquals(response.getMessage(), "User registration successful.");
    }


    /**
     * Test user Register with existing userId.
     * Exptected: failed 400 Bad request.
     */
    @Test
    public void testRegisterWithExistingUserId() {
        String userId = "user" + System.currentTimeMillis();
        User user = user(userId);

        //create user1.
        HttpEntity httpEntity = new HttpEntity(user, httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(baseURL, HttpMethod.POST, httpEntity, AuctionResponse.class);
        AuctionResponse response = (AuctionResponse) responseEntity.getBody();
        assertEquals(response.getStatusCode(), Response.Status.CREATED.getStatusCode());
        assertEquals(response.getMessage(), "User registration successful.");

        //create user2.
        responseEntity = restTemplate.exchange(baseURL, HttpMethod.POST, httpEntity, AuctionResponse.class);
        response = (AuctionResponse) responseEntity.getBody();
        assertEquals(response.getStatusCode(), Response.Status.BAD_REQUEST.getStatusCode());
        assertTrue(response.getErrorVOs().size() > 0);
        for (ErrorVO errorVO: response.getErrorVOs()) {
            assertEquals(errorVO.getMessage(), "user id being used, please try with other.");
        }
    }

    /**
     * Tests user login functionality.
     * Exptected: Success 200 OK.
     */
    @Test
    public void testLogin() {
        String userId = "user" + System.currentTimeMillis();
        User user = user(userId);

        HttpEntity httpEntity = new HttpEntity(user, httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(baseURL, HttpMethod.POST, httpEntity, AuctionResponse.class);
        AuctionResponse response = (AuctionResponse) responseEntity.getBody();
        assertEquals(response.getStatusCode(), Response.Status.CREATED.getStatusCode());
        assertEquals(response.getMessage(), "User registration successful.");

        //Login.

        responseEntity = restTemplate.exchange(baseURL + "/login", HttpMethod.POST, httpEntity, AuctionResponse.class);
        response = (AuctionResponse) responseEntity.getBody();
        assertEquals(response.getStatusCode(), Response.Status.OK.getStatusCode());
        assertEquals(response.getMessage(), "Logged in successful.");
    }

    /**
     * Test login with invalid userid.
     * Expected: failed with 404-NotFound.
     */
    @Test
    public void testLoginFailed() {
        String userId = "user" + System.currentTimeMillis();
        User user = user(userId);

        HttpEntity httpEntity = new HttpEntity(user, httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(baseURL + "/login", HttpMethod.POST, httpEntity, AuctionResponse.class);
        AuctionResponse response = (AuctionResponse) responseEntity.getBody();
        assertEquals(response.getStatusCode(), Response.Status.NOT_FOUND.getStatusCode());
        assertTrue(response.getErrorVOs().size() > 0);
        for (ErrorVO errorVO: response.getErrorVOs()) {
            assertEquals(errorVO.getMessage(), "User not found.");
        }
    }



    private User user(String userId) {
        User user = new User();
        user.setId(userId);
        user.setPassword("123456789");
        user.setFirstName("Durga");
        user.setLastName("Lovababu");
        user.setContact("8123717649");
        user.setEmail("dpadala@sapient.com");
        user.setRole("Seller");

        Address address = new Address();
        address.setDoorNumber("1-25");
        address.setLane1("Munnekollala");
        address.setCity("Bangalore");
        address.setState("KA");
        address.setCountry("IN");
        address.setZipCode("560037");
        user.setAddresses(new HashSet<Address>() {
            {
                add(address);
            }
        });

        return user;
    }

    @After
    public void tearDown() {
        restTemplate = null;
    }
}
