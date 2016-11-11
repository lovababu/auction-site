package com.sapient.auction.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * ErrorVO model/Json class, to transfer the Error information as Json message.
 *
 * Created by dpadal on 11/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ErrorVO {

    private String code;
    private String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ErrorVO errorVO = (ErrorVO) o;

        if (!code.equals(errorVO.code)) {
            return false;
        }
        return message.equals(errorVO.message);

    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }
}
