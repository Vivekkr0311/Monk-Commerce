package com.monk_commerce.SDE_Assignment.repository;

import com.monk_commerce.SDE_Assignment.entities.Coupon;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends MongoRepository<Coupon, String> {

}
