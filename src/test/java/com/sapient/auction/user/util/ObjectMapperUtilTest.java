package com.sapient.auction.user.util;

import com.sapient.auction.common.model.AddressVO;
import com.sapient.auction.common.model.SaleVO;
import com.sapient.auction.common.model.UserVO;
import com.sapient.auction.user.entity.Address;
import com.sapient.auction.user.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by dpadal on 11/15/2016.
 */
@RunWith(JUnit4.class)
public class ObjectMapperUtilTest {
    private UserVO userVO;

    @Before
    public void before() {
        AddressVO temp = AddressVO.builder()
                .withDoorNumber("20")
                .withLane1("Munnekollala")
                .withCity("Bangalore")
                .withState("karnataka")
                .withCountry("india")
                .withZipCode("5600037").build();
        AddressVO perm = AddressVO.builder()
                .withDoorNumber("1-25")
                .withLane1("BvPalem")
                .withCity("P.Gannavaram")
                .withState("AndhraPradesh")
                .withCountry("india")
                .withZipCode("533240").build();

        userVO = UserVO.builder()
                .withId("dpadal")
                .withFirstName("Durga")
                .withLastName("Lovababu")
                .withEmail("dpadala@sapient.com")
                .withPassword("123456789")
                .withContact("8123717649")
                .withAddress(Arrays.asList(temp, perm)).build();

    }

    @Test
    public void testSaleVo() {
        User user = ObjectMapperUtil.userEntity(userVO);
        assertNotNull(user);
        assertEquals(user.getId(), userVO.getId());
        assertEquals(user.getEmail(), userVO.getEmail());
        assertEquals(user.getContact(), userVO.getContact());
        assertEquals(user.getFirstName(), userVO.getFirstName());
        assertEquals(user.getLastName(), userVO.getLastName());
        assertEquals(user.getAddresses().size(), userVO.getAddresses().size());
        assertTrue(user.getAddresses().containsAll(userVO.getAddresses()));
    }
    @After
    public void tearDown() {

    }
}
