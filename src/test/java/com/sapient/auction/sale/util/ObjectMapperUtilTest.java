package com.sapient.auction.sale.util;

import com.sapient.auction.common.model.ProductVO;
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
                .build();

        ProductVO productVO = ProductVO.builder()
                .withId("124512-Len")
                .withDesc("Lenovo thinkpad.")
                .withName("Lenovo Laptop")
                .withPrice(new BigDecimal(35000))
                .withType("Electronic").build();

        saleVO = SaleVO.builder()
                .withId(1234L)
                .withStartTime(new Date())
                .withEndTime(new Date())
                .withPrice(new BigDecimal(15000))
                .withProduct(productVO)
                .withUserVO(userVO).build();
    }

    @Test
    public void testSaleVo() {
        Sale sale = ObjectMapperUtil.saleEntity(saleVO);
        assertNotNull(sale);
        assertEquals(sale.getId(), saleVO.getId());
        assertEquals(sale.getStartTime(), saleVO.getStartTime());
        assertEquals(sale.getEndTime(), saleVO.getEndTime());
        assertEquals(sale.getPrice(), saleVO.getPrice());
    }
    @After
    public void tearDown() {

    }
}
