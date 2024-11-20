package com.monk_commerce.SDE_Assignment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedCartItem {
    private Integer product_id;
    private Integer quantity;
    private Double price;
    private Double total_discount;
}
