package com.monk_commerce.SDE_Assignment.repository.BxGyCouponRepository;

import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.BxGyCoupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BxGyCouponRepository extends MongoRepository<BxGyCoupon, String> {
}
