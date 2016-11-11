package com.sapient.auction.user.util;

import com.sapient.auction.common.model.UserVO;
import com.sapient.auction.user.entity.User;
import org.springframework.beans.BeanUtils;

/**
 * Created by dpadal on 11/11/2016.
 */
public final class ObjectMapperUtil {

    public static User userEntity(UserVO userVO) {
        User userEntity = new User();
        BeanUtils.copyProperties(userVO, userEntity);
        return userEntity;
    }

    public static UserVO userVO(User userEntity) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userEntity, userVO);
        return userVO;
    }
}
