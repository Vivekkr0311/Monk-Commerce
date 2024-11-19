package com.monk_commerce.SDE_Assignment.service;

import com.monk_commerce.SDE_Assignment.entities.*;
import com.monk_commerce.SDE_Assignment.repository.CouponRepository;
import com.monk_commerce.SDE_Assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ProductRepository productRepository;

    public void save(Coupon coupon){
        String couponType = coupon.getType();

        if(couponType.equals("cart-wise")){
            couponRepository.save(coupon);
        }else if(couponType.equals("product-wise")){
            ProductWiseCouponDetails couponDetails = (ProductWiseCouponDetails) coupon.getDetails();
            Integer product_id_coupon = couponDetails.getProduct_id();

            Product product = productRepository.findById(product_id_coupon).orElse(null);
            if(product != null){
                product.setCoupon(coupon);
                productRepository.save(product);
            }
        }else if(couponType.equals("bxgy")){
            BxGyCouponDetails couponDetails = (BxGyCouponDetails) coupon.getDetails();
            List<ProductQuantity> buyProducts = couponDetails.getBuyProducts();

            for(ProductQuantity p : buyProducts){
                Product foundProduct = productRepository.findById(p.getProduct_id()).orElse(null);

                if(foundProduct != null){
                    foundProduct.setCoupon(coupon);
                    productRepository.save(foundProduct);
                }
            }
        }
        couponRepository.save(coupon);
    }

    public List<Coupon> findAll(){
        return couponRepository.findAll();
    }

    public Coupon findById(String id){
        return couponRepository.findById(id).orElse(null);
    }

    public void deleteById(String id){
        couponRepository.deleteById(id);
    }

    public void updateById(Coupon newCoupon, String id){
        Coupon oldCoupon = couponRepository.findById(id).orElse(null);
        if(newCoupon != null && oldCoupon != null){
            oldCoupon.setType(newCoupon.getType() != null && !newCoupon.getType().equals("") ? newCoupon.getType() : oldCoupon.getType());
            oldCoupon.setDetails(newCoupon.getDetails() != null && !newCoupon.getDetails().equals("") ? newCoupon.getDetails(): oldCoupon.getDetails());
            couponRepository.save(oldCoupon);
        }
    }

    public void getApplicableCoupons(List<CartItem> items){

        for(CartItem item : items){
            System.out.println(item);
        }
    }
}
