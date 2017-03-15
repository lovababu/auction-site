package com.sapient.auction.sale.service;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.sale.exception.BidNotAllowedException;
import com.sapient.auction.sale.exception.InvalidBidAmountException;
import com.sapient.auction.sale.exception.SaleNotFoundException;
import com.sapient.auction.sale.repository.SaleRepository;
import com.sapient.auction.sale.service.impl.SaleServiceImpl;
import com.sapient.auction.user.entity.User;
import com.sapient.auction.user.exception.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * JUnit test cases for SaleService, by mock the Repository class.
 * <p>
 * Created by dpadal on 11/19/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class SaleServiceTest {

    @InjectMocks
    private SaleService saleService = new SaleServiceImpl();

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private Appender mockAppender;

    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;


    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        ch.qos.logback.classic.Logger logger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        when(mockAppender.getName()).thenReturn("MOCK");
        logger.addAppender(mockAppender);

        Mockito.when(saleRepository.create(any(Sale.class))).thenReturn(fakeSale("1"));
        Mockito.when(saleRepository.detail(anyString())).thenAnswer(invocationOnMock -> {
            String id = invocationOnMock.getArgumentAt(0, String.class);
            if (id.equals("0")) {
                return null;
            } else {
                return fakeSale("1");
            }
        });

        Mockito.when(saleRepository.bid(any(Bid.class))).thenReturn(fakeBid());
        Mockito.when(saleRepository.getLatestBid(anyString())).thenReturn(fakeBid());
    }

    @Test
    public void testSaleCreate() throws UserNotFoundException {
        Sale sale = saleService.create(fakeSale("1"));
        assertNotNull(sale);
        assertNotNull(sale.getId());
        assertNotNull(sale.getStartTime());
        Mockito.verify(saleRepository, times(1)).create(sale);

        Mockito.verify(mockAppender).doAppend(captorLoggingEvent.capture());
        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();

        //Check log level is correct
        assertEquals(loggingEvent.getLevel(), Level.INFO);
        //Check the message being logged is correct
        assertEquals(loggingEvent.getFormattedMessage(), "Sale record saved in db with id: " + sale.getId());
    }

    @Test(expected = Exception.class)
    public void testSaleCreateFailed() throws UserNotFoundException {
        Mockito.when(saleRepository.create(any(Sale.class))).thenThrow(Exception.class);
        Sale fakeSale = fakeSale("1");
        Sale sale = saleService.create(fakeSale);
        assertNull(sale);
        Mockito.verify(saleRepository, times(1)).create(fakeSale);
    }

    @Test
    public void testSaleDetail() throws SaleNotFoundException {
        Sale sale = saleService.detail("1");
        assertNotNull(sale);
        assertNotNull(sale.getId());
        Mockito.verify(saleRepository, times(1)).detail("1");

        Mockito.verify(mockAppender).doAppend(captorLoggingEvent.capture());
        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();

        //Check log level is correct
        assertEquals(loggingEvent.getLevel(), Level.INFO);
        //Check the message being logged is correct
        assertEquals(loggingEvent.getFormattedMessage(), "Fetching the sale id: " + sale.getId());

    }

    @Test(expected = SaleNotFoundException.class)
    public void testSaleDetailFailed() throws SaleNotFoundException {
        Sale sale = saleService.detail("0");
        assertNull(sale);
        Mockito.verify(saleRepository, times(1)).detail("0");

        Mockito.verify(mockAppender).doAppend(captorLoggingEvent.capture());
        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();

        //Check log level is correct
        assertEquals(loggingEvent.getLevel(), Level.INFO);
        //Check the message being logged is correct
        assertEquals(loggingEvent.getFormattedMessage(), "Fetching the sale id: " + sale.getId());
    }

    @Test
    public void testSaleList() throws SaleNotFoundException {
        Mockito.when(saleRepository.list()).thenReturn(Arrays.asList(fakeSale("1")));
        List<Sale> sales = saleService.list();
        assertNotNull(sales);
        assertTrue(sales.size() > 0);
        Mockito.verify(saleRepository, times(1)).list();

        verify(mockAppender).doAppend(captorLoggingEvent.capture());
        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();

        //Check log level is correct
        assertEquals(loggingEvent.getLevel(), Level.INFO);
        //Check the message being logged is correct
        assertEquals(loggingEvent.getFormattedMessage(), "Fetched no of sales: " + sales.size());
    }

    @Test(expected = SaleNotFoundException.class)
    public void testSaleListFailed() throws SaleNotFoundException {
        Mockito.when(saleRepository.list()).thenReturn(null);
        List<Sale> sales = saleService.list();
        assertNull(sales);

        Mockito.verify(saleRepository, times(1)).list();

        verify(mockAppender, times(0)).doAppend(captorLoggingEvent.capture());

    }

    @Test(expected = SaleNotFoundException.class)
    public void testBidWhenSaleNotFound() throws SaleNotFoundException, InvalidBidAmountException, BidNotAllowedException {
        Mockito.when(saleRepository.detail(anyString())).thenReturn(null);

        Bid bid = saleService.bid("2", fakeBid());
        assertNull(bid);

        Mockito.verify(saleRepository, times(1)).detail("2");
        Mockito.verify(saleRepository, times(0)).bid(any(Bid.class));
    }

    @Test(expected = InvalidBidAmountException.class)
    public void testBidWhenBidPriceInvalid() throws SaleNotFoundException, InvalidBidAmountException, BidNotAllowedException {
        Mockito.when(saleRepository.detail(anyString())).thenReturn(fakeSale("1"));
        Bid fakeBid = fakeBid();
        fakeBid.setPrice(new BigDecimal(500));
        Bid bid = saleService.bid("2", fakeBid);
        assertNull(bid);

        Mockito.verify(saleRepository, times(1)).detail("2");
        Mockito.verify(saleRepository, times(0)).bid(any(Bid.class));
    }

    @Test(expected = BidNotAllowedException.class)
    public void testBidWhenSaleOwnerBids() throws SaleNotFoundException, InvalidBidAmountException, BidNotAllowedException {
        Sale fakeSale = fakeSale("1");
        Mockito.when(saleRepository.detail(anyString())).thenReturn(fakeSale);
        Bid fakeBid = fakeBid();
        fakeBid.setPrice(new BigDecimal(500));
        //update fake user, to match with sale owner.
        fakeBid.getUser().setEmail(fakeSale.getUser().getEmail());
        Bid bid = saleService.bid("2", fakeBid);
        assertNull(bid);

        Mockito.verify(saleRepository, times(1)).detail("2");
        Mockito.verify(saleRepository, times(0)).bid(any(Bid.class));
    }

    @Test
    public void testBid() throws SaleNotFoundException, InvalidBidAmountException, BidNotAllowedException {
        Mockito.when(saleRepository.detail(anyString())).thenReturn(fakeSale("1"));
        Mockito.when(saleRepository.bid(any(Bid.class))).thenReturn(fakeBid());
        Bid fakeBid = fakeBid();
        fakeBid.setPrice(new BigDecimal(1500));
        Bid bid = saleService.bid("2", fakeBid);
        assertNotNull(bid);
        assertNotNull(bid.getSale());

        Mockito.verify(saleRepository, times(1)).detail("2");
        Mockito.verify(saleRepository, times(1)).bid(any(Bid.class));

        Mockito.verify(mockAppender).doAppend(captorLoggingEvent.capture());
        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();

        //Check log level is correct
        assertEquals(loggingEvent.getLevel(), Level.INFO);
        //Check the message being logged is correct
        assertEquals(loggingEvent.getFormattedMessage(), "Bid record saved in db with id: " + fakeBid.getId());
    }


    @Test(expected = SaleNotFoundException.class)
    public void testGetLatestBidFailed() throws SaleNotFoundException {
        Mockito.when(saleRepository.isSaleExist(anyString())).thenReturn(false);
        Bid bid = saleService.getLatestBid("3");
        assertNull(bid);
        Mockito.verify(saleRepository, times(1)).isSaleExist("3");
    }

    @Test
    public void testGetLatestBid() throws SaleNotFoundException {
        Mockito.when(saleRepository.isSaleExist(anyString())).thenReturn(true);
        Mockito.when(saleRepository.getLatestBid(anyString())).thenReturn(fakeBid());
        Bid bid = saleService.getLatestBid("3");
        assertNotNull(bid);
        assertNotNull(bid.getUser().getEmail());
        assertNotNull(bid.getSale().getProductId());

        Mockito.verify(saleRepository, times(1)).isSaleExist("3");
        Mockito.verify(saleRepository, times(1)).getLatestBid("3");
    }

    private User fakeUser(String email) {
        User user = new User();
        user.setEmail(email);
        return user;
    }

    private Sale fakeSale(String id) {
        Sale sale = new Sale();
        sale.setId(id);
        sale.setEndTime(new Date());
        sale.setPrice(new BigDecimal(15000));
        sale.setProductId("Lenovo-3245");
        sale.setProductName("Lenovo  tab");
        sale.setProductDesc("Lenovo tablet.");
        sale.setProductType("Electronic");
        sale.setUser(fakeUser("fakeemail1@fdomain.com"));
        sale.setBids(new HashSet<Bid>() {
            {
                add(fakeBid());
            }
        });

        return sale;
    }


    private Bid fakeBid() {

        Bid fakeBid = new Bid();
        fakeBid.setId("1");
        fakeBid.setPrice(new BigDecimal(1000));
        Sale sale = new Sale();
        sale.setId("2");
        sale.setEndTime(new Date());
        sale.setPrice(new BigDecimal(15000));
        sale.setProductId("Lenovo-3245");
        sale.setProductName("Lenovo  tab");
        sale.setProductDesc("Lenovo tablet.");
        sale.setProductType("Electronic");
        fakeBid.setSale(sale);
        fakeBid.setUser(fakeUser("fakeemail2@fdomain.com"));
        return fakeBid;
    }
}