package com.sapient.auction.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.auction.common.model.SaleVO;
import com.sapient.auction.common.model.UserVO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by dpadal on 11/14/2016.
 */
public class JsonGenerator {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserVO userVO = UserVO.builder()
                .withEmail("dpadala@sapient.com")
                .withFirstName("Lovababu")
                .withLastName("Padala")
                .withPassword("12345678")
                .withContact("8123717649")
                .withAddress("Bangalore, KA").build();

        SaleVO saleVO = SaleVO.builder()
                .withEndTime(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(4)))
                .withPrice(new BigDecimal(15000))
                .withProductId("124512-Len")
                .withProductDesc("Lenovo thinkpad.")
                .withProductName("Lenovo Laptop")
                .withProductType("Electronic")
                .withProductImageUrl("http://google.com/image/a.png").build();


        objectMapper.writeValue(System.out, saleVO);
    }
}
