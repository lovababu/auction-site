package com.sapient.auction.sale.util;

import com.sapient.auction.common.model.SaleVO;
import com.sapient.auction.common.model.UserVO;
import com.sapient.auction.sale.entity.Sale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by dpadal on 11/15/2016.
 */
@RunWith(JUnit4.class)
public class ObjectMapperUtilTest {
    private SaleVO saleVO;

    @Before
    public void before() {
        UserVO userVO = UserVO.builder()
                .withId("dpadal")
                .withFirstName("Durga")
                .withLastName("Lovababu")
                .withEmail("dpadala@sapient.com")
                .withPassword("123456789")
                .withContact("8123717649")
                .withRole("Seller").build();

        saleVO = SaleVO.builder()
                .withId(1234L)
                .withStartTime(new Date())
                .withEndTime(new Date())
                .withInitialPrice(new BigDecimal(15000))
                .withProductId("Lenovo-12342")
                .withProductDesc("Lenovo thinkpad.")
                .withProductName("Lenovo Laptop")
                .withProductPrice(new BigDecimal(35000))
                .withProductType("Electronic")
                .withUserVO(userVO).build();
    }

    @Test
    public void testSaleVo() {
        Sale sale = ObjectMapperUtil.saleEntity(saleVO);
        assertNotNull(sale);
        assertEquals(sale.getId(), saleVO.getId());
    }
    @After
    public void tearDown() {

    }
}
