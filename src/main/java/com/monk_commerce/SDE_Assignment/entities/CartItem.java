package com.monk_commerce.SDE_Assignment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "cart_item")
public class CartItem {
    @Id
    private int productId;
    private int quantity;
    private double price;
    private double totalDiscount;
}
