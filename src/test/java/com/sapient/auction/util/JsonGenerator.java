package com.sapient.auction.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.auction.common.model.AddressVO;
import com.sapient.auction.common.model.SaleVO;
import com.sapient.auction.common.model.UserVO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by dpadal on 11/14/2016.
 */
public class JsonGenerator {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AddressVO temp = AddressVO.builder()
                .withDoorNumber("20")
                .withLane1("Munnekollala")
                .withCity("Bangalore")
                .withState("karnataka")
                .withCountry("india")
                .withType("Temporary")
                .withZipCode("5600037").build();
        AddressVO perm = AddressVO.builder()
                .withDoorNumber("1-25")
                .withLane1("BvPalem")
                .withCity("P.Gannavaram")
                .withState("AndhraPradesh")
                .withCountry("india")
                .withType("Permanent")
                .withZipCode("533240").build();

        UserVO userVO = UserVO.builder()
                .withId("dpadal")
                .withFirstName("Durga")
                .withLastName("Lovababu")
                .withEmail("dpadala@sapient.com")
                .withPassword("123456789")
                .withContact("8123717649")
                .withAddress(Arrays.asList(temp, perm)).build();

        SaleVO saleVO = SaleVO.builder()
                .withId(1234L)
                .withStartTime(new Date())
                .withEndTime(new Date())
                .withPrice(new BigDecimal(15000))
                .withProductId("124512-Len")
                .withProductDesc("Lenovo thinkpad.")
                .withProductName("Lenovo Laptop")
                .withProductType("Electronic")
                .withUserVO(userVO).build();


        objectMapper.writeValue(System.out, saleVO);
    }
}
