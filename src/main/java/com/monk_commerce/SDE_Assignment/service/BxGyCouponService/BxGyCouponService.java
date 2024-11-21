package com.monk_commerce.SDE_Assignment.service.BxGyCouponService;

import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.BxGyCoupon;
import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.BxGyCouponDetails;
import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.ProductQuantity;
import com.monk_commerce.SDE_Assignment.entities.Products.Product;
import com.monk_commerce.SDE_Assignment.repository.BxGyCouponRepository.BxGyCouponDetailsRepository;
import com.monk_commerce.SDE_Assignment.repository.BxGyCouponRepository.BxGyCouponRepository;
import com.monk_commerce.SDE_Assignment.repository.ProductRepository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BxGyCouponService {
    @Autowired
    private BxGyCouponRepository bxGyCouponRepository;

    @Autowired
    private BxGyCouponDetailsRepository bxGyCouponDetailsRepository;

    @Autowired
    private ProductRepository productRepository;

    public void save(BxGyCoupon bxGyCoupon){
        BxGyCouponDetails bxGyCouponDetails = bxGyCoupon.getDetails();
        bxGyCouponDetailsRepository.save(bxGyCouponDetails);
        bxGyCouponRepository.save(bxGyCoupon);

        Set<ProductQuantity> buyProductList = bxGyCouponDetails.getBuyProducts();
        Set<ProductQuantity> getProductList = bxGyCouponDetails.getGetProducts();

        // storing bxgy coupon id reference to each product id listed in the coupon
        for(ProductQuantity productQuantity : buyProductList){
            Integer product_id = productQuantity.getProduct_id();
            Product product = productRepository.findById(product_id).orElse(null);

            if(product != null){
                product.getCoupon_ids().add(bxGyCoupon.getCoupon_id());
                productRepository.save(product);
            }else{
                System.out.println("Product with product_id: {" + product_id + "} not found in db");
            }
        }

//        bxGyCouponDetailsRepository.save(bxGyCouponDetails);
//        bxGyCouponRepository.save(bxGyCoupon);
    }

    public BxGyCoupon findById(String id){
        return bxGyCouponRepository.findById(id).orElse(null);
    }

    public void deleteById(String id){
        // fetched all products
        List<Product> allProduct = productRepository.findAll();

        // now in all products, coupons list we will search for coupon id
        for(Product product : allProduct){
            HashSet<String> coupon_ids = product.getCoupon_ids();

            // if coupon id is matched then we will delete it
            if(coupon_ids.contains(id)){
                coupon_ids.remove(id);
            }
            productRepository.save(product);
        }
        bxGyCouponRepository.deleteById(id);
    }

    public List<BxGyCoupon> findAll(){
        return bxGyCouponRepository.findAll();
    }
}
