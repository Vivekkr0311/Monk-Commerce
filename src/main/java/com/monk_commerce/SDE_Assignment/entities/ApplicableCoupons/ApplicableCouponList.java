package com.monk_commerce.SDE_Assignment.entities.ApplicableCoupons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ApplicableCouponList {
    private HashSet<ApplicableCoupon> applicable_coupons = new HashSet<>();
}
