package com.monk_commerce.SDE_Assignment.repository.ProductWiseCouponRepository;

import com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon.ProductWiseCoupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductWiseCouponRepository extends MongoRepository<ProductWiseCoupon, String> {
}
