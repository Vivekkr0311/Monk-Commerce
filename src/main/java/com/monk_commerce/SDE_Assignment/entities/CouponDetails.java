package com.monk_commerce.SDE_Assignment.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type" // This property determines the concrete class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CartWiseCouponDetails.class, name = "cart-wise"),
        @JsonSubTypes.Type(value = ProductWiseCouponDetails.class, name = "product-wise"),
        @JsonSubTypes.Type(value = BxGyCouponDetails.class, name = "bxgy")
})
public interface CouponDetails {
}
