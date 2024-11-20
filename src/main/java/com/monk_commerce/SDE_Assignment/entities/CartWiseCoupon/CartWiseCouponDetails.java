package com.monk_commerce.SDE_Assignment.entities.CartWiseCoupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartWiseCouponDetails {

    @Id
    private String cart_wise_coupon_detail_id;
    private Integer threshold;
    private Integer discount;
}
