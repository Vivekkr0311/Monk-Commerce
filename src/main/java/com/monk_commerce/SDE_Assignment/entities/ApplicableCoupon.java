package com.monk_commerce.SDE_Assignment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicableCoupon {
    private List<String> coupon_id = new ArrayList<>();
    private String type;
    private Double discount;
}
