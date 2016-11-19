package com.sapient.auction.e2e;

import com.sapient.auction.SapAuctionSiteApplication;
import com.sapient.auction.common.model.AuctionResponse;
import com.sapient.auction.common.model.SaleVO;
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
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by dpadal on 11/19/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SapAuctionSiteApplication.class)
@WebIntegrationTest(randomPort = true)
public class SaleE2ETests {

    private RestTemplate restTemplate = null;

    @Value("${local.server.port}")  //boot injects the port.
    private int port;

    private String baseURL = null;


    private HttpHeaders httpHeaders;

    @Before
    public void setUp() {
        restTemplate = new TestRestTemplate();
        baseURL = "http://localhost:" + port + "/auction/sale";
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON);
    }


    /**
     * Test Sale create.
     * Expected: success 201 Created.
     */
    @Test
    public void testCreate() {
        SaleVO saleVO = sale();
        HttpEntity httpEntity = new HttpEntity(saleVO, httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(baseURL, HttpMethod.POST, httpEntity, AuctionResponse.class);
        AuctionResponse response = (AuctionResponse) responseEntity.getBody();
        assertEquals(response.getStatusCode(), Response.Status.CREATED.getStatusCode());
        assertNotNull(responseEntity.getHeaders().getLocation());
    }

    private SaleVO sale() {

        SaleVO saleVO = SaleVO.builder()
                .withId(1234L)
                .withStartTime(new Date())
                .withEndTime(new Date())
                .withPrice(new BigDecimal(15000))
                .withProductId("124512-Len")
                .withProductDesc("Lenovo thinkpad.")
                .withProductName("Lenovo Laptop")
                .withProductType("Electronic").build();
        return saleVO;
    }
}
