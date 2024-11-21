package com.monk_commerce.SDE_Assignment.service.BxGyCouponService;

import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.BxGyCouponDetails;
import com.monk_commerce.SDE_Assignment.repository.BxGyCouponRepository.BxGyCouponDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BxGyCouponDetailsService {
    @Autowired
    private BxGyCouponDetailsRepository bxGyCouponDetailsRepository;

    public void save(BxGyCouponDetails bxGyCouponDetails){
        bxGyCouponDetailsRepository.save(bxGyCouponDetails);
    }
}
