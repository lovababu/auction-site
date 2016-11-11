package com.sapient.auction.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * SaleVO model/Json class, to transfer the Sale info as Json message.
 *
 * Created by dpadal on 11/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class SaleVO {

    private String id;
    private ProductVO productVO;
    private Date startTime;
    private Date endTime;
    private BigDecimal initialPrice;

    private Set<BidVO> bidVOs;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SaleVO saleVO = (SaleVO) o;

        if (!id.equals(saleVO.id)) {
            return false;
        }
        return productVO.equals(saleVO.productVO);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + productVO.hashCode();
        return result;
    }
}
