package com.monk_commerce.SDE_Assignment.repository.ProductWiseCouponRepository;

import com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon.ProductWiseCouponDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductWiseCouponDetailsRepository extends MongoRepository<ProductWiseCouponDetails, String> {
}
