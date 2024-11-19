package com.monk_commerce.SDE_Assignment.repository;

import com.monk_commerce.SDE_Assignment.entities.Coupon;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends MongoRepository<Coupon, String> {
    public List<Coupon> findByType(String type);
}
