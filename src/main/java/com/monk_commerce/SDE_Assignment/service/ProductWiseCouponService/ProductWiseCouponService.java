package com.monk_commerce.SDE_Assignment.service.ProductWiseCouponService;

import com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon.ProductWiseCoupon;
import com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon.ProductWiseCouponDetails;
import com.monk_commerce.SDE_Assignment.entities.Products.Product;
import com.monk_commerce.SDE_Assignment.repository.ProductRepository.ProductRepository;
import com.monk_commerce.SDE_Assignment.repository.ProductWiseCouponRepository.ProductWiseCouponDetailsRepository;
import com.monk_commerce.SDE_Assignment.repository.ProductWiseCouponRepository.ProductWiseCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductWiseCouponService {
    @Autowired
    private ProductWiseCouponRepository productWiseCouponRepository;

    @Autowired
    private ProductWiseCouponDetailsRepository productWiseCouponDetailsRepository;

    @Autowired
    private ProductRepository productRepository;

    public void save(ProductWiseCoupon productWiseCoupon){
        ProductWiseCouponDetails productWiseCouponDetails = productWiseCoupon.getDetails();
        productWiseCouponDetailsRepository.save(productWiseCouponDetails);
        productWiseCouponRepository.save(productWiseCoupon);

        Product product = productRepository.findById(productWiseCouponDetails.getProduct_id()).orElse(null);
        product.getCoupon_ids().add(productWiseCoupon.getCoupon_id());
        productRepository.save(product);
    }

    public List<ProductWiseCoupon> findAll(){
        return productWiseCouponRepository.findAll();
    }

    public ProductWiseCoupon findById(String id){
        return productWiseCouponRepository.findById(id).orElse(null);
    }

    public void deleteById(String id){
        productWiseCouponRepository.deleteById(id);
    }
}
