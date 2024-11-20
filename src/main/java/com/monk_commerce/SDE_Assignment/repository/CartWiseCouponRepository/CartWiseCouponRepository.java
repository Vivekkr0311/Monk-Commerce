package com.monk_commerce.SDE_Assignment.repository.CartWiseCouponRepository;

import com.monk_commerce.SDE_Assignment.entities.CartWiseCoupon.CartWiseCoupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartWiseCouponRepository extends MongoRepository<CartWiseCoupon, String> {
}
