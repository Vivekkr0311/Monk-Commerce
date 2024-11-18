package com.monk_commerce.SDE_Assignment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BxGyCouponDetails implements CouponDetails{
    private List<ProductQuantity> buyProducts;
    private List<ProductQuantity> getProducts;
    private int repetitionLimit;
}
