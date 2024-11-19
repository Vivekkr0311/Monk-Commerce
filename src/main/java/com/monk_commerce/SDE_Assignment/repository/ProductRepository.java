package com.monk_commerce.SDE_Assignment.repository;

import com.monk_commerce.SDE_Assignment.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, Integer> {
}
