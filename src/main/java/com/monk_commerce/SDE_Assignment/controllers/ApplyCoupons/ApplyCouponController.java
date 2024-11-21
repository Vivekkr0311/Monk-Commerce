package com.monk_commerce.SDE_Assignment.controllers.ApplyCoupons;

import com.monk_commerce.SDE_Assignment.entities.ApplicableCoupons.ApplicableCoupon;
import com.monk_commerce.SDE_Assignment.entities.ApplicableCoupons.ApplicableCouponList;
import com.monk_commerce.SDE_Assignment.entities.Cart.Cart;
import com.monk_commerce.SDE_Assignment.service.ApplicableCoupons.ApplicableCouponService;
import com.monk_commerce.SDE_Assignment.service.BxGyCouponService.BxGyCouponService;
import com.monk_commerce.SDE_Assignment.service.CartWiseCouponService.CartWiseCouponService;
import com.monk_commerce.SDE_Assignment.service.ProductWiseCouponService.ProductWiseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping
public class ApplyCouponController {

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

    @PostMapping("/applicable-coupons")
    public ResponseEntity<?> applicableCoupon(@RequestBody Cart cart){
        HashSet<ApplicableCoupon> allApplicableCoupon = applicableCouponService.getApplicableCoupon(cart);
        applicableCouponList.getApplicable_coupons().addAll(allApplicableCoupon);
        return new ResponseEntity<>(
                applicableCouponList,
                HttpStatus.CREATED
        );
    }
}
