package com.sapient.auction.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * UserVO model/Json class, to transfer the User information as Json message.
 *
 * Created by dpadal on 11/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class UserVO {

    private String id;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserVO userVO = (UserVO) o;

        return id.equals(userVO.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
