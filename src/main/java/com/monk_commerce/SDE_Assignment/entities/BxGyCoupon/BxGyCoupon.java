package com.monk_commerce.SDE_Assignment.entities.BxGyCoupon;

import com.monk_commerce.SDE_Assignment.entities.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BxGyCoupon implements Coupon {

    @Id
    private String coupon_id;
    private String type = "bxgy";
    private BxGyCouponDetails details;
}
