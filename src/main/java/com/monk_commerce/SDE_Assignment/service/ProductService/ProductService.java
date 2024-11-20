package com.monk_commerce.SDE_Assignment.service.ProductService;

import com.monk_commerce.SDE_Assignment.entities.Products.Product;
import com.monk_commerce.SDE_Assignment.repository.ProductRepository.ProductRepository;
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

    public boolean deleteByID(Integer product_id){
        Product product = productRepository.findById(product_id).orElse(null);
        if(product != null){
            productRepository.deleteById(product_id);
            return true;
        }
        return false;
    }
}
