package com.monk_commerce.SDE_Assignment.service;

import com.monk_commerce.SDE_Assignment.entities.CartItem;
import com.monk_commerce.SDE_Assignment.entities.Coupon;
import com.monk_commerce.SDE_Assignment.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public void save(Coupon coupon){
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
