package com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon;

import com.monk_commerce.SDE_Assignment.entities.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWiseCoupon implements Coupon {
    @Id
    private String coupon_id;
    String type = "product-wise";
    @DBRef
    private ProductWiseCouponDetails details = new ProductWiseCouponDetails();
}
