package com.monk_commerce.SDE_Assignment.controllers.CouponController;

import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.BxGyCoupon;
import com.monk_commerce.SDE_Assignment.entities.CartWiseCoupon.CartWiseCoupon;
import com.monk_commerce.SDE_Assignment.entities.Coupon;
import com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon.ProductWiseCoupon;
import com.monk_commerce.SDE_Assignment.repository.ProductWiseCouponRepository.ProductWiseCouponRepository;
import com.monk_commerce.SDE_Assignment.service.BxGyCouponService.BxGyCouponService;
import com.monk_commerce.SDE_Assignment.service.CartWiseCouponService.CartWiseCouponService;
import com.monk_commerce.SDE_Assignment.service.ProductService.ProductService;
import com.monk_commerce.SDE_Assignment.service.ProductWiseCouponService.ProductWiseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {
    @Autowired
    private ProductWiseCouponService productWiseCouponService;

    @Autowired
    private CartWiseCouponService cartWiseCouponService;

    @Autowired
    private BxGyCouponService bxGyCouponService;

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon){
        String type = coupon.getType();

        if(type.equals("cart-wise")){
            CartWiseCoupon cartWiseCoupon = (CartWiseCoupon) coupon;
            cartWiseCouponService.save(cartWiseCoupon);
            return new ResponseEntity<>(
                    HttpStatus.CREATED
            );
        }else if(type.equals("product-wise")){
            ProductWiseCoupon productWiseCoupon = (ProductWiseCoupon) coupon;
            productWiseCouponService.save(productWiseCoupon);
            return new ResponseEntity<>(
                    HttpStatus.CREATED
            );

        }else if(type.equals("bxgy")){
            BxGyCoupon bxGyCoupon = (BxGyCoupon) coupon;
            bxGyCouponService.save(bxGyCoupon);
            return new ResponseEntity<>(
                    HttpStatus.CREATED
            );
        }
        return new ResponseEntity<>(
                HttpStatus.BAD_REQUEST
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCartWiseCoupon(){
        List<Coupon> allCoupon = new ArrayList<>();
        allCoupon.addAll(cartWiseCouponService.findAll());
        allCoupon.addAll(productWiseCouponService.findAll());
        allCoupon.addAll(bxGyCouponService.findAll());
        return new ResponseEntity<>(
                allCoupon,
                HttpStatus.FOUND
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCouponById(@PathVariable String id){
        ProductWiseCoupon productWiseCoupon = productWiseCouponService.findById(id);
        CartWiseCoupon cartWiseCoupon = cartWiseCouponService.findById(id);
        BxGyCoupon bxGyCoupon = bxGyCouponService.findById(id);
        Boolean isDeleted = false;
        if(productWiseCoupon != null && cartWiseCoupon == null && bxGyCoupon == null){
            productWiseCouponService.deleteById(id);
            isDeleted = true;
        }else if(productWiseCoupon == null && cartWiseCoupon != null && bxGyCoupon == null){
            cartWiseCouponService.deleteById(id);
            isDeleted = true;
        }else if(productWiseCoupon == null && cartWiseCoupon == null && bxGyCoupon != null){
            bxGyCouponService.deleteById(id);
            isDeleted = true;
        }

        if(isDeleted){
            List<Coupon> allCoupon = new ArrayList<>();
            allCoupon.addAll(cartWiseCouponService.findAll());
            allCoupon.addAll(productWiseCouponService.findAll());
            allCoupon.addAll(bxGyCouponService.findAll());
            return new ResponseEntity<>(
                    allCoupon,
                HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
            HttpStatus.NOT_FOUND
        );
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<?> getCouponById(@PathVariable String id){
        CartWiseCoupon coupon = cartWiseCouponService.findById(id);
        if(coupon != null){
            return new ResponseEntity<>(
                    coupon,
                    HttpStatus.FOUND
            );
        }else{
            return new ResponseEntity<>(
                    "No coupon with id: {" + id + "}",
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
