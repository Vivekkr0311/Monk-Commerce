package com.monk_commerce.SDE_Assignment.service.ApplicableCouponsService;

import com.monk_commerce.SDE_Assignment.entities.ApplicableCoupons.ApplicableCoupon;
import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.BxGyCoupon;
import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.BxGyCouponDetails;
import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.ProductQuantity;
import com.monk_commerce.SDE_Assignment.entities.Cart.Cart;
import com.monk_commerce.SDE_Assignment.entities.Cart.CartItem;
import com.monk_commerce.SDE_Assignment.entities.CartWiseCoupon.CartWiseCoupon;
import com.monk_commerce.SDE_Assignment.entities.CartWiseCoupon.CartWiseCouponDetails;
import com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon.ProductWiseCoupon;
import com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon.ProductWiseCouponDetails;
import com.monk_commerce.SDE_Assignment.service.BxGyCouponService.BxGyCouponService;
import com.monk_commerce.SDE_Assignment.service.CartWiseCouponService.CartWiseCouponService;
import com.monk_commerce.SDE_Assignment.service.ProductService.ProductService;
import com.monk_commerce.SDE_Assignment.service.ProductWiseCouponService.ProductWiseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
public class ApplicableCouponService {
    @Autowired
    private CartWiseCouponService cartWiseCouponService;

    @Autowired
    private ProductWiseCouponService productWiseCouponService;

    @Autowired
    private BxGyCouponService bxGyCouponService;

    @Autowired
    private ProductService productService;

    public HashSet<ApplicableCoupon> getApplicableCoupon(Cart cart){
        Double totalCartPrice = getTotalCartPrice(cart);
        HashSet<ApplicableCoupon> applicableCouponsHashSet = new HashSet<>();
        // applying cart wise coupon
        applicableCouponsHashSet.addAll(getCartWiseApplicableCoupons(cart, totalCartPrice));

        // applying product wise coupon
        applicableCouponsHashSet.addAll(getProductWiseApplicableCoupons(cart));

        // applying bxgy coupon
        applicableCouponsHashSet.addAll(getBxGyApplicableCoupon(cart));

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

    private HashSet<ApplicableCoupon> getCartWiseApplicableCoupons(Cart cart, Double totalCartPrice){
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

        return cartWiseCouponApplicableHashSet;
    }

    private HashSet<ApplicableCoupon> getProductWiseApplicableCoupons(Cart cart){
        HashSet<CartItem> allItemInTheCart = cart.getCart().getItems();
        HashSet<ApplicableCoupon> productWiseCouponApplicableHashSet = new HashSet<>();
        for(CartItem eachCartItem : allItemInTheCart){
            Integer currentItemProductId = eachCartItem.getProduct_id();
            Integer currentItemQuantity = eachCartItem.getQuantity();

            List<ProductWiseCoupon> productWiseCouponList = productWiseCouponService.findAll();
            for(ProductWiseCoupon productWiseCoupon : productWiseCouponList){
                ProductWiseCouponDetails productWiseCouponDetails = productWiseCoupon.getDetails();

                if(productWiseCouponDetails.getProduct_id() == currentItemProductId){
                    // found a product wise coupon for the current cart item

                    ApplicableCoupon applicableCoupon = new ApplicableCoupon();
                    applicableCoupon.setCoupon_id(productWiseCoupon.getCoupon_id());
                    applicableCoupon.setType(productWiseCoupon.getType());

                    Double discountByCoupon = productWiseCouponDetails.getDiscount();
                    Double cartItemPrice = eachCartItem.getPrice();
                    Double discountToCurrentCartItem = currentItemQuantity.doubleValue() * cartItemPrice * (discountByCoupon / 100);

                    applicableCoupon.setDiscount(discountToCurrentCartItem);

                    productWiseCouponApplicableHashSet.add(applicableCoupon);
                }
            }
        }

        return productWiseCouponApplicableHashSet;
    }

    private HashSet<ApplicableCoupon> getBxGyApplicableCoupon(Cart cart){
        List<BxGyCoupon> bxGyCouponList = bxGyCouponService.findAll();
        HashSet<ApplicableCoupon> applicableCouponHashSet = new HashSet<>();

        if(bxGyCouponList.isEmpty()){
            return new HashSet<>();
        }

        for(BxGyCoupon eachBxGyCoupon : bxGyCouponList){
            BxGyCouponDetails bxGyCouponDetails = eachBxGyCoupon.getDetails();
            HashSet<ProductQuantity> mandatoryProductToBuy = bxGyCouponDetails.getBuyProducts();
            HashSet<ProductQuantity> productThatWillBeFree = bxGyCouponDetails.getGetProducts();

            Boolean allMandatoryItemExists = checkIfAllMandatoryProductExistInCurrentCart(mandatoryProductToBuy, cart);
            Boolean allFreeItemExist = checkIfFreeItemsExistsInTheCart(productThatWillBeFree, cart);

            // If all mandatory Item exist then make buy product free
            if(allMandatoryItemExists){
                // check if the discounted item exists in the cart
                if(allFreeItemExist){
                    ApplicableCoupon applicableCoupon = new ApplicableCoupon();

                    applicableCoupon.setCoupon_id(eachBxGyCoupon.getCoupon_id());
                    applicableCoupon.setType(eachBxGyCoupon.getType());
                    Double discount = getDiscountBxGyCalculation(eachBxGyCoupon, cart);
                    applicableCoupon.setDiscount(discount);
                    applicableCouponHashSet.add(applicableCoupon);
                }
            }

        }
        return applicableCouponHashSet;
    }

    private boolean checkIfAllMandatoryProductExistInCurrentCart(HashSet<ProductQuantity> mandatoryProductToBuy, Cart cart){
        HashSet<CartItem> itemsInTheCart = cart.getCart().getItems();
        HashMap<Integer, Integer> itemInTheCartProductIdsAndTheirQuantity = cartItemProductIdHashSet(itemsInTheCart);

        Boolean isAllMandatoryProductExistsInTheCart = false;
        for(ProductQuantity eachMandatoryProduct : mandatoryProductToBuy){
            // below are the product id and product quantity required in the cart
            Integer eachMandatoryProductsProductId = eachMandatoryProduct.getProduct_id();
            Integer eachMandatoryProductQuantity = eachMandatoryProduct.getQuantity();

            // now we are checking if the mandatory product id exist in the cart product ids
            if(itemInTheCartProductIdsAndTheirQuantity.containsKey(eachMandatoryProductsProductId)){

                // if it exists, then we check for their quantities
                Integer currentQuantityInTheCart = itemInTheCartProductIdsAndTheirQuantity.get(eachMandatoryProductsProductId);
                if(currentQuantityInTheCart >= eachMandatoryProductQuantity){
                    isAllMandatoryProductExistsInTheCart = true;
                }
            }else{
                isAllMandatoryProductExistsInTheCart = false;
                break;
            }
        }
        return isAllMandatoryProductExistsInTheCart;
    }

    private HashMap<Integer, Integer> cartItemProductIdHashSet(HashSet<CartItem> itemsInTheCart){
        HashMap<Integer, Integer> cartItemProductIdHashSet = new HashMap<>();

        for(CartItem item : itemsInTheCart){
            cartItemProductIdHashSet.put(item.getProduct_id(), item.getQuantity());
        }
        return cartItemProductIdHashSet;
    }

    private boolean checkIfFreeItemsExistsInTheCart(HashSet<ProductQuantity> productsThatWillBeFree, Cart cart){
        HashSet<CartItem> currentItemInTheCart = cart.getCart().getItems();
        HashMap<Integer, Integer> itemInTheCartProductIdsAndTheirQuantity = cartItemProductIdHashSet(currentItemInTheCart);

        Boolean isAllFreeItemExistsInTheCart = false;
        for(ProductQuantity eachFreeItem : productsThatWillBeFree){
            // below are the product id and free items required in the cart
            Integer eachFreeItemProductId = eachFreeItem.getProduct_id();
            Integer eachFreeItemProductQuantity = eachFreeItem.getQuantity();

            // now we are checking if the free item product id exist in the cart product ids
            if(itemInTheCartProductIdsAndTheirQuantity.containsKey(eachFreeItemProductId)){

                // if it exists, then we check for their quantities
                Integer currentQuantityInTheCart = itemInTheCartProductIdsAndTheirQuantity.get(eachFreeItemProductId);
                if(currentQuantityInTheCart >= eachFreeItemProductQuantity){
                    isAllFreeItemExistsInTheCart = true;
                }
            }else{
                isAllFreeItemExistsInTheCart = false;
                break;
            }
        }
        return isAllFreeItemExistsInTheCart;
    }

    private Double getDiscountBxGyCalculation(BxGyCoupon bxGyCoupon, Cart cart){
        HashSet<CartItem> currentItemsInTheCart = cart.getCart().getItems();
        BxGyCouponDetails bxGyCouponDetails = bxGyCoupon.getDetails();
        HashSet<ProductQuantity> quantity = bxGyCouponDetails.getGetProducts();

        Double discount = 0.0;
        for(CartItem itemInTheCart : currentItemsInTheCart){
            Integer product_id = itemInTheCart.getProduct_id();

            for(ProductQuantity productQuantity : quantity){
                if(productQuantity.getProduct_id() == product_id){
                    Integer q = productQuantity.getQuantity();
                    Double price = itemInTheCart.getPrice();

                    discount = discount + q * price;
                }
            }
        }
        return discount;
    }
}
