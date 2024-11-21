package com.monk_commerce.SDE_Assignment.entities.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemWrapper {
    private HashSet<CartItem> items;
}
