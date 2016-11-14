package com.sapient.auction.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.auction.common.model.SaleVO;
import com.sapient.auction.common.model.UserVO;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

/**
 * Created by dpadal on 11/14/2016.
 */
public class JsonGenerator {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserVO userVO = UserVO.builder()
                .withId("dpadal")
                .withFirstName("Durga")
                .withLastName("Lovababu")
                .withEmail("dpadala@sapient.com")
                .withPassword("123456789")
                .withContact("8123717649")
                .withRole("Seller").build();

        SaleVO saleVO = SaleVO.builder()
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


        objectMapper.writeValue(System.out, userVO);
    }
}
