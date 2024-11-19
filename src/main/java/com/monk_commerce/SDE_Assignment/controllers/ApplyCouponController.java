package com.monk_commerce.SDE_Assignment.controllers;

import com.monk_commerce.SDE_Assignment.entities.Cart;
import com.monk_commerce.SDE_Assignment.entities.CartItem;
import com.monk_commerce.SDE_Assignment.entities.Coupon;
import com.monk_commerce.SDE_Assignment.entities.CouponResponse;
import com.monk_commerce.SDE_Assignment.service.ApplicableCouponService;
import com.monk_commerce.SDE_Assignment.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/applicable-coupons")
public class ApplyCouponController {
    @Autowired
    private CouponService couponService;

    @Autowired
    private ApplicableCouponService applicableCouponService;

    @PostMapping
    public ResponseEntity<?> applicableCoupons(@RequestBody Cart cart){
        CouponResponse couponResponse = applicableCouponService.getApplicableCoupons(cart);
        return new ResponseEntity<>(
                couponResponse,
                HttpStatus.OK
        );
    }
}
