package com.monk_commerce.SDE_Assignment.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "coupon")
public class Coupon {
    @Id
    @Indexed
    private String id;

    @NonNull
    private String type;

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type"
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = CartWiseCouponDetails.class, name = "cart-wise"),
            @JsonSubTypes.Type(value = ProductWiseCouponDetails.class, name = "product-wise"),
            @JsonSubTypes.Type(value = BxGyCouponDetails.class, name = "bxgy")
    })
    @NonNull
    private CouponDetails details;
}
