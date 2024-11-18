package com.monk_commerce.SDE_Assignment.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWiseCouponDetails implements CouponDetails{
    @JsonProperty("product_id")
    private int product_id;
    @JsonProperty("discount")
    private double discount;
}
