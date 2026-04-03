package com.foodservice.frontend.service;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.foodservice.frontend.entity.dto.OrderCouponDTO;
import com.foodservice.frontend.util.ApiGetRequest;

@Service
public class CouponClientService {

    private final ApiGetRequest<List<OrderCouponDTO>> apiGetRequest;

    public CouponClientService(ApiGetRequest<List<OrderCouponDTO>> apiGetRequest) {
        this.apiGetRequest = apiGetRequest;
    }

    // Fetch coupons for a given order
    public List<OrderCouponDTO> getCouponsByOrder(int orderId, String token) {

        return apiGetRequest.get(
                "/coupons/order/" + orderId,
                Collections.emptyMap(),
                token,
                new ParameterizedTypeReference<>() {}
        );
    }
}