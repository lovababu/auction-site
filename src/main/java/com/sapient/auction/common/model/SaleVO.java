package com.sapient.auction.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * SaleVO model/Json class, to transfer the Sale info as Json message.
 *
 * Created by dpadal on 11/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public final class SaleVO {

    private Long id;
    private ProductVO productVO;
    private UserVO userVO;
    private Date startTime;
    private Date endTime;
    private BigDecimal initialPrice;
    private Set<BidVO> bidVOs;

    public SaleVO(Builder builder) {
        this.id = builder.id;
        this.productVO = builder.productVO;
        this.userVO = builder.userVO;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.initialPrice = builder.initialPrice;
        this.bidVOs = builder.bidVOs;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private ProductVO productVO;
        private UserVO userVO;
        private Date startTime;
        private Date endTime;
        private BigDecimal initialPrice;
        private Set<BidVO> bidVOs;

        public Builder() {
            this.productVO = new ProductVO();
        }

        public SaleVO build() {
            return new SaleVO(this);
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withStartTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder withEndTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }
        public Builder withInitialPrice(BigDecimal initialPrice) {
            this.initialPrice = initialPrice;
            return this;
        }
        public Builder withProductId(String id) {
            this.productVO.setId(id);
            return this;
        }
        public Builder withProductName(String name) {
            this.productVO.setName(name);
            return this;
        }

        public Builder withProductDesc(String desc) {
            this.productVO.setDesc(desc);
            return this;
        }

        public Builder withProductType(String type) {
            this.productVO.setType(type);
            return this;
        }

        public Builder withProductPrice(BigDecimal price) {
            this.productVO.setPrice(price);
            return this;
        }

        public Builder withUserVO(UserVO userVO) {
            this.userVO =  userVO;
            return this;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter @Setter
    private static class ProductVO {

        private String id;
        private String name;
        private String type;
        private String desc;
        private BigDecimal price;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SaleVO saleVO = (SaleVO) o;

        return id.equals(saleVO.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
