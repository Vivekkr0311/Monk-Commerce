package com.monk_commerce.SDE_Assignment.service.ProductService;

import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.BxGyCoupon;
import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.BxGyCouponDetails;
import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.ProductQuantity;
import com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon.ProductWiseCoupon;
import com.monk_commerce.SDE_Assignment.entities.Products.Product;
import com.monk_commerce.SDE_Assignment.repository.ProductRepository.ProductRepository;
import com.monk_commerce.SDE_Assignment.service.BxGyCouponService.BxGyCouponService;
import com.monk_commerce.SDE_Assignment.service.CartWiseCouponService.CartWiseCouponService;
import com.monk_commerce.SDE_Assignment.service.ProductWiseCouponService.ProductWiseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductWiseCouponService productWiseCouponService;

    @Autowired
    private CartWiseCouponService cartWiseCouponService;

    @Autowired
    private BxGyCouponService bxGyCouponService;

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public Product findProductById(Integer product_id){
        return productRepository.findById(product_id).orElse(null);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public boolean deleteByID(Integer product_id){
        Product product = productRepository.findById(product_id).orElse(null);
        deleteRespectiveProductWiseCoupon(product);
        deleteRespectiveBxGyCoupon(product);
        if(product != null){
            productRepository.deleteById(product_id);
            return true;
        }
        return false;
    }

    private void deleteRespectiveProductWiseCoupon(Product product){
        HashSet<String> allCouponsId = product.getCoupon_ids();

        for(String coupon_id : allCouponsId){
            ProductWiseCoupon productWiseCoupon = productWiseCouponService.findById(coupon_id);

            if(productWiseCoupon != null){
                productWiseCouponService.deleteById(coupon_id);
            }
        }
    }

    private void deleteRespectiveBxGyCoupon(Product product){
        HashSet<String> allCouponId = product.getCoupon_ids();

        for(String coupon_id : allCouponId){
            BxGyCoupon bxGyCoupon = bxGyCouponService.findById(coupon_id);
            if(bxGyCoupon != null){
                BxGyCouponDetails bxGyCouponDetails = bxGyCoupon.getDetails();

                Set<ProductQuantity> buyProduct = bxGyCouponDetails.getBuyProducts();
                Set<ProductQuantity> getProduct = bxGyCouponDetails.getGetProducts();

                for(ProductQuantity buyProductQuantity : buyProduct){
                    if(buyProductQuantity.getProduct_id() == product.getProduct_id()){
                        buyProduct.remove(buyProductQuantity);
                    }
                }

                for(ProductQuantity getProductQuantity : getProduct){
                    if(getProductQuantity.getProduct_id() == product.getProduct_id()){
                        buyProduct.remove(getProductQuantity);
                    }
                }

                bxGyCouponService.save(bxGyCoupon);
            }
        }
    }
}
