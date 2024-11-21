package com.monk_commerce.SDE_Assignment.entities.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cart")
public class Cart {
    @Id
    private Integer cartId;
    private CartItemWrapper cart;
}
