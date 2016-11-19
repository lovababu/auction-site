package com.sapient.auction.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * SaleVO model/Json class, to transfer the Sale info as Json message.
 * <p>
 * Created by dpadal on 11/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
public final class SaleVO {

    private Long id;
    private Date startTime;
    private Date endTime;
    private BigDecimal price;

    private String productId;
    private String productName;
    private String productType;
    private String productDesc;
    private String productImageUrl;

    private UserVO userVO;
    private Set<BidVO> bidVOs;

    public SaleVO(Builder builder) {
        this.id = builder.id;
        this.userVO = builder.userVO;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.price = builder.price;
        this.productId = builder.productId;
        this.productName = builder.productName;
        this.productDesc = builder.productDesc;
        this.productType = builder.productType;
        this.productImageUrl = builder.productImageUrl;
        this.bidVOs = builder.bidVOs;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private UserVO userVO;
        private Date startTime;
        private Date endTime;
        private BigDecimal price;
        private String productId;
        private String productName;
        private String productType;
        private String productDesc;
        private String productImageUrl;
        private Set<BidVO> bidVOs;

        public Builder() {
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

        public Builder withPrice(BigDecimal initialPrice) {
            this.price = initialPrice;
            return this;
        }

        public Builder withProductId(String id) {
            this.productId = id;
            return this;
        }

        public Builder withProductName(String name) {
            this.productName = name;
            return this;
        }

        public Builder withProductType(String type) {
            this.productType = type;
            return this;
        }

        public Builder withProductDesc(String desc) {
            this.productDesc = desc;
            return this;
        }

        public Builder withImageUrl(String imageUrl) {
            this.productImageUrl = imageUrl;
            return this;
        }

        public Builder withUserVO(UserVO userVO) {
            this.userVO = userVO;
            return this;
        }
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

        if (id.equals(saleVO.id)) {
            return true;
        }

        return this.productId.equals(saleVO.getProductId());
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + productId.hashCode();
        return result;
    }
}
