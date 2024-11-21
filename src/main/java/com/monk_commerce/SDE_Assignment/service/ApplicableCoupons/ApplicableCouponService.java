package com.monk_commerce.SDE_Assignment.service.ApplicableCoupons;

import com.monk_commerce.SDE_Assignment.entities.ApplicableCoupons.ApplicableCoupon;
import com.monk_commerce.SDE_Assignment.entities.Cart.Cart;
import com.monk_commerce.SDE_Assignment.entities.Cart.CartItem;
import com.monk_commerce.SDE_Assignment.entities.CartWiseCoupon.CartWiseCoupon;
import com.monk_commerce.SDE_Assignment.entities.CartWiseCoupon.CartWiseCouponDetails;
import com.monk_commerce.SDE_Assignment.service.CartWiseCouponService.CartWiseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ApplicableCouponService {
    @Autowired
    private CartWiseCouponService cartWiseCouponService;

    public HashSet<ApplicableCoupon> getApplicableCoupon(Cart cart){
        Double totalCartPrice = getTotalCartPrice(cart);
        HashSet<ApplicableCoupon> applicableCouponsHashSet = new HashSet<>();

        // applying cart wise coupon
        applicableCouponsHashSet.addAll(applyCartWiseCoupon(cart, totalCartPrice));

        // applying product wise coupon

        return applicableCouponsHashSet;
    }

    private Double getTotalCartPrice(Cart cart){
        Double totalCartPrice = 0.0;

        for(CartItem eachCartItem : cart.getCart().getItems()){
            Integer quantity = eachCartItem.getQuantity();
            Double price = eachCartItem.getPrice();
            totalCartPrice = totalCartPrice + quantity * price;
        }
        return totalCartPrice;
    }

    private HashSet<ApplicableCoupon> applyCartWiseCoupon(Cart cart, Double totalCartPrice){
        List<CartWiseCoupon> allCartWiseCoupon = cartWiseCouponService.findAll();

        Double currentDiscount = 0.0;
        HashSet<ApplicableCoupon> cartWiseCouponApplicableHashSet = new HashSet<>();
        for(CartWiseCoupon cartWiseCoupon : allCartWiseCoupon){
            CartWiseCouponDetails cartWiseCouponDetails = cartWiseCoupon.getDetails();
            Double thresholdOfCurrentCartWiseCoupon = cartWiseCouponDetails.getThreshold();

            if(thresholdOfCurrentCartWiseCoupon <= totalCartPrice){
                ApplicableCoupon cartWiseApplicableCoupon = new ApplicableCoupon();
                // setting current coupon id to new object of current applicable cart wise coupon
                cartWiseApplicableCoupon.setCoupon_id(cartWiseCoupon.getCoupon_id());

                // it is setting coupon type to this current object of new applicable cart wise coupon
                cartWiseApplicableCoupon.setType(cartWiseCoupon.getType());

                // setting total discount to this new object of cart wise applicable coupon

                Double discount = cartWiseCouponDetails.getDiscount();
                currentDiscount = totalCartPrice *  (discount / 100);
                cartWiseApplicableCoupon.setDiscount(currentDiscount);

                cartWiseCouponApplicableHashSet.add(cartWiseApplicableCoupon);
            }
        }

        if(cartWiseCouponApplicableHashSet.isEmpty()){
            return null;
        }
        return cartWiseCouponApplicableHashSet;
    }

//    private HashSet<ApplicableCoupon> applyProductWiseCoupon(Cart cart){
//        for(CartItem eachCartItem : car)
//    }
}
