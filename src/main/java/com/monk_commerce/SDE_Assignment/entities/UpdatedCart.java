package com.monk_commerce.SDE_Assignment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Document(collection = "updated_cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedCart {
    private UpdatedCartItemWrapper updatedCart;
    private Double total_price;
    private Double total_discount;
    private Double final_price;
}
