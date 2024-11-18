package com.monk_commerce.SDE_Assignment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BxGyCouponDetails implements CouponDetails{
    private List<ProductQuantity> buyProducts;
    private List<ProductQuantity> getProducts;
    private int repitiionLimit;
}
