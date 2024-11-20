package com.monk_commerce.SDE_Assignment.repository.CartWiseCouponRepository;

import com.monk_commerce.SDE_Assignment.entities.CartWiseCoupon.CartWiseCouponDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartWiseCouponDetailsRepository extends MongoRepository<CartWiseCouponDetails, String> {
}
