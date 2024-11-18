package com.monk_commerce.SDE_Assignment.controllers;

import com.monk_commerce.SDE_Assignment.entities.Coupon;
import com.monk_commerce.SDE_Assignment.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @PostMapping
    public void makeCoupon(@RequestBody Coupon coupon){
        couponService.save(coupon);
    }

    @GetMapping
    public ResponseEntity<?> getAllCoupon(){
        List<Coupon> allCoupon = couponService.findAll();

        if(allCoupon.isEmpty()){
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                couponService.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCouponByID(@PathVariable String id){
        Coupon coupon = couponService.findById(id);
        if(coupon == null){
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                coupon,
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCouponByID(@RequestBody Coupon coupon, @PathVariable String id){
        Coupon oldCoupon = couponService.findById(id);
        if(coupon == null){
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        couponService.updateById(coupon, id);
        return new ResponseEntity<>(
                coupon,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable String id){
        Coupon oldCoupon = couponService.findById(id);
        if(oldCoupon== null){
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        couponService.deleteById(id);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }
}
