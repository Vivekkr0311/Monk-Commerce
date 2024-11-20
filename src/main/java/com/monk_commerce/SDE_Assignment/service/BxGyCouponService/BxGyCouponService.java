package com.monk_commerce.SDE_Assignment.service.BxGyCouponService;

import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.BxGyCoupon;
import com.monk_commerce.SDE_Assignment.repository.BxGyCouponRepository.BxGyCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BxGyCouponService {
    @Autowired
    private BxGyCouponRepository bxGyCouponRepository;

    public BxGyCoupon findById(String id){
        return bxGyCouponRepository.findById(id).orElse(null);
    }

    public void deleteById(String id){
        bxGyCouponRepository.deleteById(id);
    }
}
