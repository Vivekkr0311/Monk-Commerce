package com.monk_commerce.SDE_Assignment.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartWiseCouponDetails implements CouponDetails{
    @JsonProperty("threshold")
    private double threshold;
    @JsonProperty("discount")
    private double discount;
}
