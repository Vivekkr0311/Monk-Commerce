package com.monk_commerce.SDE_Assignment.service;

import com.monk_commerce.SDE_Assignment.entities.Product;
import com.monk_commerce.SDE_Assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public Product findProductById(Integer product_id){
        return productRepository.findById(product_id).orElse(null);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public void deleteByID(Integer product_id){
        productRepository.deleteById(product_id);
    }
}
