package com.monk_commerce.SDE_Assignment.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.monk_commerce.SDE_Assignment.entities.BxGyCoupon.BxGyCoupon;
import com.monk_commerce.SDE_Assignment.entities.CartWiseCoupon.CartWiseCoupon;
import com.monk_commerce.SDE_Assignment.entities.ProductWiseCoupon.ProductWiseCoupon;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CartWiseCoupon.class, name = "cart-wise"),
        @JsonSubTypes.Type(value = ProductWiseCoupon.class, name = "product-wise"),
        @JsonSubTypes.Type(value = BxGyCoupon.class, name = "bxgy")
})
public interface Coupon {
    String getType();
}