package com.sapient.auction.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by dpadal on 11/17/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
public class ProductVO {

    private String id;
    private String name;
    private String type;
    private String desc;
    private BigDecimal price;
    private String imageUrl;

    public ProductVO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.type = builder.type;
        this.desc = builder.desc;
        this.price = builder.price;
        this.imageUrl = builder.imageUrl;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String name;
        private String type;
        private String desc;
        private BigDecimal price;
        private String imageUrl;

        public ProductVO build() {
            return new ProductVO(this);
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }
        public Builder withDesc(String desc) {
            this.desc = desc;
            return this;
        }
        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }
        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
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

        ProductVO productVO = (ProductVO) o;

        if (!id.equals(productVO.id)) {
            return false;
        }
        return name.equals(productVO.name);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
