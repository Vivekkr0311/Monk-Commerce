package com.monk_commerce.SDE_Assignment.controllers;

import com.monk_commerce.SDE_Assignment.entities.Product;
import com.monk_commerce.SDE_Assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        productService.saveProduct(product);

        try{
            return new ResponseEntity<>(
                    productService.findProductById(product.getProduct_id()),
                    HttpStatus.CREATED
            );
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer product_id){
        Product product = productService.findProductById(product_id);

        if(product == null){
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                product,
                HttpStatus.FOUND
        );
    }

    @GetMapping("/all-products")
    public ResponseEntity<?> findAllProducts(){
        List<Product> allProducts = productService.findAll();

        if(allProducts.isEmpty()){
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                allProducts,
                HttpStatus.FOUND
        );
    }

    @DeleteMapping("/delete/{product_id}")
    public ResponseEntity<?> deleteProductByID(@PathVariable Integer product_id){
        productService.deleteByID(product_id);

        Product product = productService.findProductById(product_id);
        if(product == null){
            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT
            );
        }
        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );
    }
}
