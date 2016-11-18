package com.sapient.auction.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by dpadal on 11/17/2016.
 */
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class AddressVO {

    private String doorNumber;
    private String lane1;
    private String lane2;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String type;

    public AddressVO(Builder builder) {
        this.doorNumber = builder.doorNumber;
        this.lane1 = builder.lane1;
        this.lane2 = builder.lane2;
        this.city = builder.city;
        this.state = builder.state;
        this.country = builder.country;
        this.zipCode = builder.zipCode;
        this.type = builder.type;

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String doorNumber;
        private String lane1;
        private String lane2;
        private String city;
        private String state;
        private String country;
        private String zipCode;
        private String type;

        public AddressVO build() {
            return new AddressVO(this);
        }

        public Builder withDoorNumber(String doorNumber) {
            this.doorNumber =doorNumber;
            return this;
        }

        public Builder withLane1(String lane1) {
            this.lane1 = lane1;
            return this;
        }

        public Builder withLane2(String lane2) {
            this.lane2 = lane2;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withState(String state) {
            this.state = state;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withZipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }
    }
}
