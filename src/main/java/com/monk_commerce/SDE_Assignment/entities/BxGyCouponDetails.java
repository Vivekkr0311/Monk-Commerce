package com.monk_commerce.SDE_Assignment.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BxGyCouponDetails implements CouponDetails{
    @JsonProperty("buy_products")
    private List<ProductQuantity> buyProducts;

    @JsonProperty("get_products")
    private List<ProductQuantity> getProducts;

    @JsonProperty("repition_limit")
    private int repetitionLimit;
}
