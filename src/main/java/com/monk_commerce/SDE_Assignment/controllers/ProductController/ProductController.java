package com.monk_commerce.SDE_Assignment.controllers.ProductController;

import com.monk_commerce.SDE_Assignment.entities.Products.Product;
import com.monk_commerce.SDE_Assignment.service.ProductService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        try{
            productService.saveProduct(product);
            return new ResponseEntity<>(
                    product,
                    HttpStatus.CREATED
            );
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @DeleteMapping("/delete/{product_id}")
    public ResponseEntity<?> createProduct(@PathVariable Integer product_id){
        try{
            Boolean deleted = productService.deleteByID(product_id);
            if(deleted){
                return new ResponseEntity<>(
                        HttpStatus.NO_CONTENT
                );
            }else{
                return new ResponseEntity<>(
                        "No such product found",
                        HttpStatus.NOT_FOUND
                );
            }
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(){
        try{
            List<Product> allProducts = productService.findAll();
            return new ResponseEntity<>(
                    allProducts,
                    HttpStatus.OK
            );
        }catch (Exception e){
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
