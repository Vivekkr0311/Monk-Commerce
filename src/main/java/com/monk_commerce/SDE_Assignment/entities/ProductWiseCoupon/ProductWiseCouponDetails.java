package com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWiseCouponDetails {

    @Id
    private String product_wise_coupon_detail_id;
    private Integer product_id;
    private Double discount;
}
