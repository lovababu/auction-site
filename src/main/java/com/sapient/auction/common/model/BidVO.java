package com.sapient.auction.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * BidVO model/Json class, to transfer the Bid information as Json message.
 *
 * Created by dpadal on 11/10/2016.
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

        if (!userId.equals(bidVO.userId)) {
            return false;
        }
        return saleVO.equals(bidVO.saleVO);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + saleVO.hashCode();
        return result;
    }
}
