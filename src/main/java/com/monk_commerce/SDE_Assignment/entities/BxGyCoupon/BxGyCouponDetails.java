package com.monk_commerce.SDE_Assignment.entities.BxGyCoupon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BxGyCouponDetails {
    @Id
    private String bxgy_coupon_detail_id;

    @JsonProperty("buy_products")
    private HashSet<ProductQuantity> buyProducts = new HashSet<>();

    @JsonProperty("get_products")
    private HashSet<ProductQuantity> getProducts = new HashSet<>();

    @JsonProperty("repition_limit")
    private int repetitionLimit;
}
