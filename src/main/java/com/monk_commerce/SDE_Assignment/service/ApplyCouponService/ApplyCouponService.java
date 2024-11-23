package com.monk_commerce.SDE_Assignment.service.ApplyCouponService;

import com.monk_commerce.SDE_Assignment.entities.ApplicableCoupons.ApplicableCoupon;
import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.BxGyCoupon;
import com.monk_commerce.SDE_Assignment.entities.Cart.Cart;
import com.monk_commerce.SDE_Assignment.entities.Cart.CartItem;
import com.monk_commerce.SDE_Assignment.entities.CartWiseCoupon.CartWiseCoupon;
import com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon.ProductWiseCoupon;
import com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon.ProductWiseCouponDetails;
import com.monk_commerce.SDE_Assignment.entities.UpdateCart.UpdateCart;
import com.monk_commerce.SDE_Assignment.entities.UpdateCart.UpdateCartItem;
import com.monk_commerce.SDE_Assignment.entities.UpdateCart.UpdateCartItemWrapper;
import com.monk_commerce.SDE_Assignment.service.ApplicableCouponsService.ApplicableCouponService;
import com.monk_commerce.SDE_Assignment.service.BxGyCouponService.BxGyCouponService;
import com.monk_commerce.SDE_Assignment.service.CartWiseCouponService.CartWiseCouponService;
import com.monk_commerce.SDE_Assignment.service.ProductService.ProductService;
import com.monk_commerce.SDE_Assignment.service.ProductWiseCouponService.ProductWiseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ApplyCouponService {
    @Autowired
    private CartWiseCouponService cartWiseCouponService;

    @Autowired
    private ProductWiseCouponService productWiseCouponService;

    @Autowired
    private BxGyCouponService bxGyCouponService;

    @Autowired
    private ApplicableCouponService applicableCouponService;

    @Autowired
    private ProductService productService;

    public UpdateCart applyCoupons(Cart cart, String coupon_id){
        UpdateCart updatedCart = new UpdateCart();
        HashSet<ApplicableCoupon> applicableCoupons = applicableCouponService.getApplicableCoupon(cart);
        ApplicableCoupon applicableCoupon = getApplicableCouponsBasedOnItsType(applicableCoupons, coupon_id);

        if(applicableCoupon != null){
            // apply coupon
            updatedCart = applyCouponHelper(applicableCoupon, cart);
        }
        return updatedCart;
    }

    private ApplicableCoupon getApplicableCouponsBasedOnItsType(HashSet<ApplicableCoupon> applicableCoupons, String coupon_id){
        ApplicableCoupon applicableCouponFound = null;

        for(ApplicableCoupon applicableCoupon : applicableCoupons){
            String coupon_id_of_each_coupon = applicableCoupon.getCoupon_id();

            if(coupon_id_of_each_coupon.equals(coupon_id)){
                applicableCouponFound = applicableCoupon;
                break;
            }
        }

        return applicableCouponFound;
    }

    private UpdateCart applyCouponHelper(ApplicableCoupon applicableCoupon, Cart cart){
        UpdateCart updatedCart = new UpdateCart();
        if(applicableCoupon.getType().equals("cart-wise")){
            // apply cart-wise coupon
            updatedCart = applyCartWiseCoupon(applicableCoupon, cart);

        }else if(applicableCoupon.getType().equals("product-wise")){
            updatedCart = applyProductWiseCoupon(applicableCoupon, cart);
        }

        return updatedCart;
    }

    private UpdateCart applyCartWiseCoupon(ApplicableCoupon cartWiseCoupon, Cart cart){
        UpdateCart updatedCart = new UpdateCart();
        updatedCart.setUpdated_cart(new UpdateCartItemWrapper());

        UpdateCartItemWrapper updateCartItemWrapper = updatedCart.getUpdated_cart();
        updateCartItemWrapper.setItems(new HashSet<>());

        HashSet<UpdateCartItem> updateCartItemHashSet = updateCartItemWrapper.getItems();

        Double totalPrice = 0.0;

        for(CartItem cartItem : cart.getCart().getItems()){
            totalPrice = totalPrice + cartItem.getQuantity() * cartItem.getPrice();
        }

        for(CartItem item : cart.getCart().getItems()){
            UpdateCartItem updateCartItem = new UpdateCartItem();

            updateCartItem.setProduct_id(item.getProduct_id());
            updateCartItem.setQuantity(item.getQuantity());
            updateCartItem.setPrice(item.getPrice());
            updateCartItem.setTotal_discount(0.0);
            updateCartItemHashSet.add(updateCartItem);
        }

        Double final_discount = totalPrice - cartWiseCoupon.getDiscount();

        updateCartItemWrapper.setFinal_price(final_discount);
        updateCartItemWrapper.setTotal_price(totalPrice);
        updateCartItemWrapper.setTotal_discount(cartWiseCoupon.getDiscount());

        return updatedCart;
    }

    private UpdateCart applyProductWiseCoupon(ApplicableCoupon productWiseCoupon, Cart cart){
        UpdateCart updatedCart = new UpdateCart();
        updatedCart.setUpdated_cart(new UpdateCartItemWrapper());

        UpdateCartItemWrapper updateCartItemWrapper = updatedCart.getUpdated_cart();
        updateCartItemWrapper.setItems(new HashSet<>());

        HashSet<UpdateCartItem> updateCartItemHashSet = updateCartItemWrapper.getItems();

        String coupon_id_from_coupon = productWiseCoupon.getCoupon_id();
        ProductWiseCoupon productWiseCoupon1 = productWiseCouponService.findById(coupon_id_from_coupon);
        ProductWiseCouponDetails productWiseCouponDetails = productWiseCoupon1.getDetails();
        Integer product_id_ = productWiseCouponDetails.getProduct_id();

        for(CartItem item : cart.getCart().getItems()){
            if(item.getProduct_id().equals(product_id_)){
                UpdateCartItem updateCartItem = new UpdateCartItem();

                updateCartItem.setProduct_id(item.getProduct_id());
                updateCartItem.setQuantity(item.getQuantity());
                updateCartItem.setPrice(item.getPrice());

                Double discount = productWiseCoupon.getDiscount();
                updateCartItem.setTotal_discount(discount);
                updateCartItemHashSet.add(updateCartItem);
            }
        }

        return updatedCart;
    }
}
