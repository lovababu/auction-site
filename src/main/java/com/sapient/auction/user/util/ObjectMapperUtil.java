package com.sapient.auction.user.util;

import com.sapient.auction.common.model.AddressVO;
import com.sapient.auction.common.model.UserVO;
import com.sapient.auction.user.entity.Address;
import com.sapient.auction.user.entity.User;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dpadal on 11/11/2016.
 */
public final class ObjectMapperUtil {

    public static User userEntity(UserVO userVO) {
        User userEntity = new User();
        BeanUtils.copyProperties(userVO, userEntity);
        Set<Address> addresses = null;
        if (userVO.getAddresses() != null) {
            addresses = new HashSet<>(userVO.getAddresses().size());
            for (AddressVO addressVO : userVO.getAddresses()) {
                Address addressEntity = new Address();
                addressEntity.setUser(userEntity);
                BeanUtils.copyProperties(addressVO, addressEntity);
                addresses.add(addressEntity);
            }
        }
        userEntity.setAddresses(addresses);
        return userEntity;
    }

    public static UserVO userVO(User userEntity) {
        UserVO userVO = new UserVO();
        //BeanUtils.copyProperties(userEntity, userVO, "id");
        return userVO;
    }
}
