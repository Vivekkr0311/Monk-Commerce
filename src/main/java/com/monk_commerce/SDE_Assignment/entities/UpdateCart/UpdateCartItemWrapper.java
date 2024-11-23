package com.monk_commerce.SDE_Assignment.entities.UpdateCart;

import com.monk_commerce.SDE_Assignment.entities.Cart.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItemWrapper {
    private HashSet<UpdateCartItem> items = new HashSet<>();
    private Double total_price;
    private Double total_discount;
    private Double final_price;
}
