package com.monk_commerce.SDE_Assignment.entities.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Integer product_id;
    private Integer quantity;
    private Double price;
}
