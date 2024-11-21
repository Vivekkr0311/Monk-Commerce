package com.monk_commerce.SDE_Assignment.service.CartWiseCouponService;

import com.monk_commerce.SDE_Assignment.repository.CartWiseCouponRepository.CartWiseCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartWiseCouponDetailService {
    @Autowired
    private CartWiseCouponRepository cartWiseCouponRepository;
}
