package com.monk_commerce.SDE_Assignment.controllers.ApplyCoupon;

import com.monk_commerce.SDE_Assignment.entities.ApplicableCoupons.ApplicableCoupon;
import com.monk_commerce.SDE_Assignment.entities.ApplicableCoupons.ApplicableCouponList;
import com.monk_commerce.SDE_Assignment.entities.Cart.Cart;
import com.monk_commerce.SDE_Assignment.entities.UpdateCart.UpdateCart;
import com.monk_commerce.SDE_Assignment.service.ApplicableCouponsService.ApplicableCouponService;
import com.monk_commerce.SDE_Assignment.service.ApplyCouponService.ApplyCouponService;
import com.monk_commerce.SDE_Assignment.service.BxGyCouponService.BxGyCouponService;
import com.monk_commerce.SDE_Assignment.service.CartWiseCouponService.CartWiseCouponService;
import com.monk_commerce.SDE_Assignment.service.ProductWiseCouponService.ProductWiseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("/apply-coupon")
public class ApplyCoupon {
    @Autowired
    private CartWiseCouponService cartWiseCouponService;

    @Autowired
    private ProductWiseCouponService productWiseCouponService;

    @Autowired
    private BxGyCouponService bxGyCouponService;

    @Autowired
    private ApplicableCouponService applicableCouponService;

    @Autowired
    private ApplicableCouponList applicableCouponList;

    @Autowired
    private ApplyCouponService applyCouponService;

    @PostMapping("/{coupon_id}")
    public ResponseEntity<?> applyCoupon(@RequestBody Cart cart, @PathVariable String coupon_id){
//        HashSet<ApplicableCoupon> allApplicableCoupon = applicableCouponService.getApplicableCoupon(cart);

        UpdateCart updatedCart = applyCouponService.applyCoupons(cart, coupon_id);
        return new ResponseEntity<>(
                updatedCart,
                HttpStatus.CREATED
        );
    }
}
