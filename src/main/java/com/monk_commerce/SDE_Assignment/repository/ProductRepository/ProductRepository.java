package com.monk_commerce.SDE_Assignment.repository.ProductRepository;

import com.monk_commerce.SDE_Assignment.entities.Products.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, Integer> {
}
