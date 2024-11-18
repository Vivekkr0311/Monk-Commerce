package com.monk_commerce.SDE_Assignment.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantity {
    @JsonProperty("product_id")
    private int product_id;
    @JsonProperty("quantity")
    private int quantity;
}
