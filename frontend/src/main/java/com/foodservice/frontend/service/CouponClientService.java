package com.foodservice.frontend.service;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.foodservice.frontend.entity.dto.OrderCouponDTO;
import com.foodservice.frontend.util.ApiGetRequest;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class CouponClientService {

    private final WebClient webClient;


    // Fetch coupons for a given order
    public List<OrderCouponDTO> getCouponsByOrder(int orderId, String token) {

        ApiGetRequest<List<OrderCouponDTO>> apiGetRequest = new ApiGetRequest<>(webClient);

        return apiGetRequest.get(
                "/coupons/order/" + orderId,
                Collections.emptyMap(),
                token,
                new ParameterizedTypeReference<>() {}
        );
    }
}