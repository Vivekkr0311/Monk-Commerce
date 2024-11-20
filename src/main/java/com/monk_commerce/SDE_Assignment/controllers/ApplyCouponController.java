package com.monk_commerce.SDE_Assignment.controllers;

import com.monk_commerce.SDE_Assignment.entities.*;
import com.monk_commerce.SDE_Assignment.service.ApplicableCouponService;
import com.monk_commerce.SDE_Assignment.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplyCouponController {
    @Autowired
    private CouponService couponService;

    @Autowired
    private ApplicableCouponService applicableCouponService;

    @PostMapping("/applicable-coupons")
    public ResponseEntity<?> applicableCoupons(@RequestBody Cart cart){
        CouponResponse couponResponse = applicableCouponService.getApplicableCoupons(cart);
        return new ResponseEntity<>(
                couponResponse,
                HttpStatus.OK
        );
    }

    @PostMapping("/apply-coupon/{coupon_id}")
    public ResponseEntity<?> applicableCoupons(@RequestBody Cart cart, @PathVariable String coupon_id){
        UpdatedCart updatedCart = applicableCouponService.applyCoupon(cart, coupon_id);
        return new ResponseEntity<>(
                updatedCart,
                HttpStatus.OK
        );
    }
}
