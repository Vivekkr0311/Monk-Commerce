package com.monk_commerce.SDE_Assignment.repository.BxGyCouponRepository;

import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.BxGyCouponDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BxGyCouponDetailsRepository extends MongoRepository<BxGyCouponDetails, String> {
}
