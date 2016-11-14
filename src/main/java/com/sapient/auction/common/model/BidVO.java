package com.sapient.auction.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dpadal on 11/14/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class BidVO {

    private String id;
    private UserVO userId;
    private SaleVO saleVO;
    private BigDecimal price;
    private Date time;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BidVO bidVO = (BidVO) o;

        if (!id.equals(bidVO.id)) {
            return false;
        }
        return userId.getId().equals(bidVO.userId.getId());

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userId.getId().hashCode();
        return result;
    }
}
