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
        ApplicableCoupon applicableCouponsBxGy = applyBxGyCoupon(allCoupons, cart);
        couponResponse.getApplicableCoupons().add(applicableCouponsBxGy);

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
                    if(coupon.getType().equals("product-wise")){
                        ProductWiseCouponDetails productWiseCouponDetails = (ProductWiseCouponDetails) coupon.getDetails();
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

    private ApplicableCoupon applyBxGyCoupon(List<Coupon> allCoupons, Cart cart){
        ApplicableCoupon applicableCoupon = new ApplicableCoupon();
        List<ApplicableCoupon> applicableCouponList = new ArrayList<>();
        Double discount = 0.0;
        for(Coupon coupon : allCoupons){

            if(coupon.getType().equals("bxgy")){
                discount = calculateBxGyDiscount(coupon, cart);
                if(discount > 0){
                    applicableCoupon.getCoupon_id().add(coupon.getId());
                    applicableCoupon.setType("bxgy");
                    applicableCoupon.setDiscount(discount);
                    applicableCouponList.add(applicableCoupon);
                }
            }
        }

        Double totalDiscount = 0.0;
        for (ApplicableCoupon bxgyCoupon : applicableCouponList){
            totalDiscount = totalDiscount + bxgyCoupon.getDiscount();
        }

        ApplicableCoupon applicableCoupon1 = new ApplicableCoupon();
        applicableCoupon1.setDiscount(totalDiscount);
        applicableCoupon1.setCoupon_id(applicableCouponList.get(0).getCoupon_id());
        applicableCoupon1.setType(applicableCouponList.get(0).getType());
        return applicableCoupon1;
    }

    private Double calculateBxGyDiscount(Coupon coupon, Cart cartItem){
        BxGyCouponDetails bxGyCouponDetails = (BxGyCouponDetails) coupon.getDetails();
        Double discount = 0.0;
        List<ProductQuantity> couponBuyProducts = bxGyCouponDetails.getBuyProducts();
        List<ProductQuantity> couponGetProducts = bxGyCouponDetails.getGetProducts();
        Integer couponRepetitionLimit = bxGyCouponDetails.getRepetitionLimit();

        if(couponRepetitionLimit <= 0){
            return discount;
        }

        // Check if all the products in the bxgy coupon is in the current cart
        Boolean allRequiredItemInTheCart = true;
        int itemInCartIdx = 0;
        int itemWhichShouldBeInCartIdx = 0;
        List<CartItem> cartItems = cartItem.getCart().getItems();
        while(itemInCartIdx < cartItem.getCart().getItems().size() && itemWhichShouldBeInCartIdx < couponBuyProducts.size()){
            if(cartItems.get(itemInCartIdx).getProduct_id() != couponBuyProducts.get(itemWhichShouldBeInCartIdx).getProduct_id()){
                allRequiredItemInTheCart = false;
            }else{
                allRequiredItemInTheCart = true;
            }
            itemInCartIdx++;
            itemWhichShouldBeInCartIdx++;
        }

        List<CartItem> freeItemsInTheCart = new ArrayList<>();
        if(allRequiredItemInTheCart){
            // If the cart is eligible for the coupon, then check if the free item exists in the cart
            itemInCartIdx = 0;
            int itemThatFreeIdx = 0;
            cartItems = cartItem.getCart().getItems();
            while(itemInCartIdx < cartItem.getCart().getItems().size() && itemThatFreeIdx < couponGetProducts.size()){
                if(cartItems.get(itemInCartIdx).getProduct_id() == couponGetProducts.get(itemThatFreeIdx).getProduct_id()){
                    freeItemsInTheCart.add(cartItems.get(itemInCartIdx));
                }
                itemInCartIdx++;
                itemWhichShouldBeInCartIdx++;
            }
        }

        for(CartItem freeItems : freeItemsInTheCart){
            discount = discount + freeItems.getPrice();
        }

        return discount;
    }

    public UpdatedCart applyCoupon(Cart cart, String coupon_id){
        System.out.println(cart);
        CouponResponse couponResponse = getApplicableCoupons(cart);

        UpdatedCart updatedCart = new UpdatedCart();

        for(ApplicableCoupon coupon : couponResponse.getApplicableCoupons()){
           List<String> allCoupons = coupon.getCoupon_id();

           for(String c : allCoupons){
               if(c.equals(coupon_id)){
                   // I need to apply coupon here
                   applyCouponUsingCoupon(coupon, cart);
                   System.out.println(coupon);
               }
           }
        }

        return new UpdatedCart();
    }

    private UpdatedCart applyCouponUsingCoupon(ApplicableCoupon coupon, Cart cart){
        UpdatedCart updatedCart = new UpdatedCart();
        if(coupon.getType().equals("cart-wise")){
            // calculate total
            // you have discount in coupon
            // just make the response and send back

            Double total = calculateTotalCartPrice(cart);
            updatedCart = applyCartWiseCoupon(coupon, cart, total);
        }else if(coupon.getType().equals("product-wise")){
            // iterate through the cart
            // make discount while travelling through coupon
            // make the response and send back
        }else if(coupon.getType().equals("bxgy")){
            // I don't know yet.
        }
        return updatedCart;
    }

    private UpdatedCart applyCartWiseCoupon(ApplicableCoupon cartWiseCoupon, Cart cart, Double total){
       String couponType = cartWiseCoupon.getType();
       String coupon_id = String.valueOf(cartWiseCoupon.getCoupon_id());
       Double discount = cartWiseCoupon.getDiscount();

       UpdatedCartItemWrapper updatedCartItemWrapper = new UpdatedCartItemWrapper();
       for(CartItem item :  cart.getCart().getItems()){
           UpdatedCartItem updatedCartItem = new UpdatedCartItem();
           updatedCartItem.setPrice(item.getPrice());
           updatedCartItem.setProduct_id(item.getProduct_id());
           updatedCartItem.setQuantity(item.getQuantity());
           updatedCartItem.setTotal_discount(0.0);

           updatedCartItemWrapper.getItems().add(updatedCartItem);
       }

       UpdatedCart updatedCart = new UpdatedCart();
       updatedCart.setUpdatedCart(updatedCartItemWrapper);
       updatedCart.setTotal_discount(total);
       updatedCart.setFinal_price(total - discount);
       return updatedCart;
    }
}
