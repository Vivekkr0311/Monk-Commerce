package com.monk_commerce.SDE_Assignment.service.ProductWiseCouponService;

import com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon.ProductWiseCoupon;
import com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon.ProductWiseCouponDetails;
import com.monk_commerce.SDE_Assignment.entities.Products.Product;
import com.monk_commerce.SDE_Assignment.repository.ProductRepository.ProductRepository;
import com.monk_commerce.SDE_Assignment.repository.ProductWiseCouponRepository.ProductWiseCouponDetailsRepository;
import com.monk_commerce.SDE_Assignment.repository.ProductWiseCouponRepository.ProductWiseCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
        HashSet<String> coupons = product.getCoupon_ids();
        if(!coupons.contains(productWiseCoupon.getCoupon_id())){
            coupons.add(productWiseCoupon.getCoupon_id());
        }else{
            System.out.println("Already this coupon exists with id: {" + productWiseCoupon.getCoupon_id() + "}");
        }
        productRepository.save(product);
    }

    public List<ProductWiseCoupon> findAll(){
        return productWiseCouponRepository.findAll();
    }

    public ProductWiseCoupon findById(String id){
        return productWiseCouponRepository.findById(id).orElse(null);
    }

    public void deleteById(String id){
        List<Product> allProducts = productRepository.findAll();

        for(Product product : allProducts){
            HashSet<String> allCouponOfThisProduct = product.getCoupon_ids();

            if(allCouponOfThisProduct.contains(id)){
                allCouponOfThisProduct.remove(id);
                productRepository.save(product);
            }else{
                System.out.println("No such coupon with product id: {" + product.getProduct_id() + "} and coupon id: {" + id + "}");
            }
        }
        deleteProductWiseCouponDetails(id);
        productWiseCouponRepository.deleteById(id);
    }

    private void deleteProductWiseCouponDetails(String id){
        ProductWiseCoupon productWiseCoupon = productWiseCouponRepository.findById(id).orElse(null);

        if(productWiseCoupon != null){
            ProductWiseCouponDetails productWiseCouponDetails = productWiseCoupon.getDetails();
            String productWiseCouponeDetailsId = productWiseCouponDetails.getProduct_wise_coupon_detail_id();

            productWiseCouponDetailsRepository.deleteById(productWiseCouponeDetailsId);
        }
    }
}
