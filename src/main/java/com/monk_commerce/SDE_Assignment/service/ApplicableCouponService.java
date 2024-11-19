package com.monk_commerce.SDE_Assignment.service;

import com.monk_commerce.SDE_Assignment.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApplicableCouponService {

    @Autowired
    private CouponService couponService;

    @Autowired
    private ProductService productService;

    public CouponResponse getApplicableCoupons(Cart cart){
        List<Coupon> allCoupons = getAllCouponsOfCart(cart);
        Double totalCartAmount = calculateTotalCartPrice(cart);
        CouponResponse couponResponse = new CouponResponse();

        // For cart-wise discount
        ApplicableCoupon applicableCouponCartWise = calculateCartWiseCouponDiscount(allCoupons, totalCartAmount);
        if(!applicableCouponCartWise.getCoupon_id().isEmpty()){
            couponResponse.getApplicableCoupons().add(applicableCouponCartWise);
        }

        // For product-wise discount
        List<ApplicableCoupon> applicableCouponProductWise = calculateProductWiseDiscount(allCoupons, cart);
        if(applicableCouponProductWise != null){
            couponResponse.getApplicableCoupons().addAll(applicableCouponProductWise);
        }

        System.out.println(allCoupons);
        System.out.println(couponResponse);
        return couponResponse;
    }

    private Double calculateTotalCartPrice(Cart cart){
        Double totalCartPrice = 0.0;

        if (cart != null && cart.getCart() != null && cart.getCart().getItems() != null) {
            List<CartItem> items = cart.getCart().getItems();

            for(CartItem item : items){
                totalCartPrice += item.getPrice() * item.getQuantity();
            }
        }
        return totalCartPrice;
    }

    private ApplicableCoupon calculateCartWiseCouponDiscount(List<Coupon> allCoupons, Double totalCartPrice){
        ApplicableCoupon applicableCoupon = new ApplicableCoupon();
        if(!allCoupons.isEmpty() && allCoupons != null){
            for(Coupon coupon :  allCoupons){
                if(coupon.getType().equals("cart-wise")){
                    CartWiseCouponDetails cartWiseCouponDetails = (CartWiseCouponDetails) coupon.getDetails();
                    if(totalCartPrice >= cartWiseCouponDetails.getThreshold()){
                        Double discountCalculated = totalCartPrice * (cartWiseCouponDetails.getDiscount() / 100);
                        applicableCoupon.setType("cart-wise");
                        applicableCoupon.setDiscount(discountCalculated);
                        applicableCoupon.getCoupon_id().add(coupon.getId());
                    }
                }
            }
        }
        return applicableCoupon;
    }

    private List<ApplicableCoupon> calculateProductWiseDiscount(List<Coupon> allCoupons, Cart cart){
        Double discount = 0.0;
        ApplicableCoupon applicableCoupon = new ApplicableCoupon();
        List<ApplicableCoupon> applicableCouponList = new ArrayList<>();
        String coupon_id = new String();
        String coupon_type = new String();
        HashMap<Integer, CartItem> product_id_not_matched = new HashMap<>();
        if(!allCoupons.isEmpty() && allCoupons != null){
            List<CartItem> items = cart.getCart().getItems();
            for(CartItem item : items){
                Integer product_id_from_cart = item.getProduct_id();
                Integer quantity = item.getQuantity();
                Double price = item.getPrice();

                for(Coupon coupon : allCoupons){
                    System.out.println(coupon.getType());
                    if(coupon.getType().equals("product-wise")){
                        ProductWiseCouponDetails productWiseCouponDetails = (ProductWiseCouponDetails) coupon.getDetails();
                        System.out.println(productWiseCouponDetails);
                        if(productWiseCouponDetails.getProduct_id() == product_id_from_cart){
                            discount = discount + quantity * price * (productWiseCouponDetails.getDiscount() /100);
                            coupon_id = coupon.getId();
                            coupon_type = coupon.getType();
                            applicableCoupon.getCoupon_id().add(coupon_id);
                            break;
                        }
                    }
                }
            }
        }
        applicableCoupon.setDiscount(discount);
        applicableCoupon.setType(coupon_type);
        applicableCouponList.add(applicableCoupon);
        return applicableCoupon.getDiscount() == 0.0 ? null : applicableCouponList;
    }

    private List<Coupon> getAllCouponsOfCart(Cart cart){
        List<Coupon> allCoupons = new ArrayList<>();
        allCoupons.addAll(couponService.findByType("cart-wise"));

        if (cart != null && cart.getCart() != null && cart.getCart().getItems() != null) {
            List<CartItem> items = cart.getCart().getItems();

            for(CartItem item : items){

                Product product = productService.findProductById(item.getProduct_id());
                if(product == null){
                    continue;
                }
                Coupon couponForThisProduct = product.getCoupon();
                if(couponForThisProduct != null){
                    allCoupons.add(couponForThisProduct);
                }
            }
        }
        return allCoupons;
    }
}
