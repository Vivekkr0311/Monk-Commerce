package com.monk_commerce.SDE_Assignment.service.CartWiseCouponService;

import com.monk_commerce.SDE_Assignment.entities.CartWiseCoupon.CartWiseCoupon;
import com.monk_commerce.SDE_Assignment.entities.CartWiseCoupon.CartWiseCouponDetails;
import com.monk_commerce.SDE_Assignment.entities.Coupon;
import com.monk_commerce.SDE_Assignment.entities.Products.Product;
import com.monk_commerce.SDE_Assignment.repository.CartWiseCouponRepository.CartWiseCouponDetailsRepository;
import com.monk_commerce.SDE_Assignment.repository.CartWiseCouponRepository.CartWiseCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartWiseCouponService {
    @Autowired
    private CartWiseCouponRepository cartWiseCouponRepository;

    @Autowired
    private CartWiseCouponDetailsRepository cartWiseCouponDetailsRepository;

    public void save(CartWiseCoupon cartWiseCoupon){
        CartWiseCouponDetails cartWiseCouponDetails = cartWiseCoupon.getDetails();
        System.out.println(cartWiseCouponDetails);
        cartWiseCouponDetailsRepository.save(cartWiseCouponDetails);
        cartWiseCouponRepository.save(cartWiseCoupon);
    }

    public List<CartWiseCoupon> findAll(){
        return cartWiseCouponRepository.findAll();
    }

    public boolean deleteById(String id){
        CartWiseCoupon cartWiseCoupon = cartWiseCouponRepository.findById(id).orElse(null);
        try{
            if(cartWiseCoupon != null){
                cartWiseCouponRepository.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public CartWiseCoupon findById(String id){
        try{
            CartWiseCoupon found = cartWiseCouponRepository.findById(id).orElse(null);
            return found;
        }catch (Exception e){
            return null;
        }
    }
}
