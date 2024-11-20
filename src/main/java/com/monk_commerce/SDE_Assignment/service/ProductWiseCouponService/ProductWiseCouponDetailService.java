package com.monk_commerce.SDE_Assignment.service.ProductWiseCouponService;

import com.monk_commerce.SDE_Assignment.repository.ProductWiseCouponRepository.ProductWiseCouponDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductWiseCouponDetailService {
    @Autowired
    private ProductWiseCouponDetailsRepository productWiseCouponDetailsRepository;
}
