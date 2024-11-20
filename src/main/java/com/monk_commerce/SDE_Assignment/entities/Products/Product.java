package com.monk_commerce.SDE_Assignment.entities.Products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product {

    @Id
    private Integer product_id;
    private Double price;
    private Integer quantity;
    private HashSet<String> coupon_ids;
}
