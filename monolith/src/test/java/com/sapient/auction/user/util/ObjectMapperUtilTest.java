package com.sapient.auction.user.util;

import com.sapient.auction.common.model.UserVO;
import com.sapient.auction.user.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by dpadal on 11/15/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ObjectMapperUtilTest {
    private UserVO userVO;

    @Before
    public void before() {
        userVO = UserVO.builder()
                .withId("dpadal")
                .withFirstName("Durga")
                .withLastName("Lovababu")
                .withEmail("dpadala@sapient.com")
                .withPassword("123456789")
                .withContact("8123717649")
                .withAddress("Bangalore").build();

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
        assertEquals(user.getAddress(), userVO.getAddress());
    }
    @After
    public void tearDown() {

    }
}
