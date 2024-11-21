package com.monk_commerce.SDE_Assignment.entities.ApplicableCoupons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ApplicableCoupon {
    @Id
    @Indexed
    private String coupon_id;
    private String type;
    private Double discount;
}
