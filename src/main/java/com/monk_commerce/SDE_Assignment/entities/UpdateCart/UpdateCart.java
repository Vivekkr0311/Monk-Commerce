package com.monk_commerce.SDE_Assignment.entities.UpdateCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "updated_cart")
public class UpdateCart {
    private UpdateCartItemWrapper updated_cart;
}
