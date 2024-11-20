package com.monk_commerce.SDE_Assignment.entities.CartWiseCoupon;

import com.monk_commerce.SDE_Assignment.entities.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartWiseCoupon implements Coupon {

    @Id
    private String coupon_id;
    private String type = "cart-wise";
    @DBRef
    private CartWiseCouponDetails details = new CartWiseCouponDetails();
}
