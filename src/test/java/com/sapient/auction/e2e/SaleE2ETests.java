package com.sapient.auction.e2e;

import com.sapient.auction.common.model.AuctionResponse;
import com.sapient.auction.common.model.SaleVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * E2E test cases for Sale Module.
 * <p>
 * Created by dpadal on 11/19/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class SaleE2ETests extends BaseE2ETest {

    @Before
    public void setUp() {
        super.setUp();
    }


    /**
     * Test Sale create.
     * Expected: success 201 Created.
     */
    @Test
    public void testCreate() throws NoSuchAlgorithmException {
        String email = "dpadala_sale@sapient.com";
        //ceate user .
        AuctionResponse response = registerUser(email);
        assertEquals(response.getStatusCode(), Response.Status.CREATED.getStatusCode());
        assertEquals(response.getMessage(), "User registration successful.");

        //setting auth header.
        setAuthHeader(email, "password1");
        //create sale.
        SaleVO saleVO = sale(Optional.of(email));
        HttpEntity httpEntity = new HttpEntity(saleVO, httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(baseURL.concat("/sale"), HttpMethod.POST, httpEntity, AuctionResponse.class);
        response = (AuctionResponse) responseEntity.getBody();
        assertEquals(response.getStatusCode(), Response.Status.CREATED.getStatusCode());
        assertNotNull(responseEntity.getHeaders().getLocation());
    }

    /**
     * Test Sale Detail.
     * Expected: success 200 Created.
     */
    @Test
    public void testDetail() throws NoSuchAlgorithmException {
        String email = "dpadala_sale2@sapient.com";
        //Create user .
        AuctionResponse response = registerUser(email);
        assertEquals(response.getStatusCode(), Response.Status.CREATED.getStatusCode());
        assertEquals(response.getMessage(), "User registration successful.");

        //setting auth header.
        setAuthHeader(email, "password1");
        //Create sale.
        SaleVO saleVO = sale(Optional.of(email));
        HttpEntity httpEntity = new HttpEntity(saleVO, httpHeaders);
        ResponseEntity createResponse = restTemplate.exchange(baseURL.concat("/sale"), HttpMethod.POST, httpEntity, AuctionResponse.class);
        response = (AuctionResponse) createResponse.getBody();
        assertEquals(response.getStatusCode(), Response.Status.CREATED.getStatusCode());
        assertNotNull(createResponse.getHeaders().getLocation());

        //Get Sale.
        HttpEntity getHttpEntity = new HttpEntity(httpHeaders);
        ResponseEntity getResponseEntity = restTemplate.exchange(createResponse.getHeaders().getLocation(),
                HttpMethod.GET, getHttpEntity, AuctionResponse.class);
        response = (AuctionResponse) getResponseEntity.getBody();
        assertEquals(response.getStatusCode(), Response.Status.OK.getStatusCode());
        assertNotNull(response.getSaleVOs().size() > 0);
        for (SaleVO s: response.getSaleVOs()) {
            assertNotNull(s.getId());
            assertEquals(s.getUser().getEmail(), saleVO.getUser().getEmail());
            assertEquals(s.getProductId(), saleVO.getProductId());
        }
    }

    /**
     * Test Sale List.
     * Expected: success 200 Created.
     */
    @Test
    public void testSaleList() {
        String email = "dpadala_sale2@sapient.com";
        //Create user .
        AuctionResponse response = registerUser(email);
        assertEquals(response.getStatusCode(), Response.Status.CREATED.getStatusCode());
        assertEquals(response.getMessage(), "User registration successful.");

        //Create sale1.
        SaleVO saleVO = sale(Optional.of(email));
        HttpEntity httpEntity = new HttpEntity(saleVO, httpHeaders);
        ResponseEntity createResponse = restTemplate.exchange(baseURL.concat("/sale"), HttpMethod.POST, httpEntity, AuctionResponse.class);
        response = (AuctionResponse) createResponse.getBody();
        assertEquals(response.getStatusCode(), Response.Status.CREATED.getStatusCode());
        assertNotNull(createResponse.getHeaders().getLocation());

        //Create sale2.
        httpEntity = new HttpEntity(saleVO, httpHeaders);
        createResponse = restTemplate.exchange(baseURL.concat("/sale"), HttpMethod.POST, httpEntity, AuctionResponse.class);
        response = (AuctionResponse) createResponse.getBody();
        assertEquals(response.getStatusCode(), Response.Status.CREATED.getStatusCode());
        assertNotNull(createResponse.getHeaders().getLocation());

        //List Sale.
        HttpEntity getHttpEntity = new HttpEntity(httpHeaders);
        ResponseEntity getResponseEntity = restTemplate.exchange(baseURL.concat("/sale/list"),
                HttpMethod.GET, getHttpEntity, AuctionResponse.class);
        response = (AuctionResponse) getResponseEntity.getBody();
        assertEquals(response.getStatusCode(), Response.Status.OK.getStatusCode());
        assertNotNull(response.getSaleVOs().size() == 2);
        for (SaleVO s: response.getSaleVOs()) {
            assertNotNull(s.getId());
            assertNotNull(s.getUser());
            assertEquals(s.getUser().getEmail(), saleVO.getUser().getEmail());
            assertEquals(s.getProductId(), saleVO.getProductId());
            assertEquals(s.getBids().size(), 0);
        }
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

}
