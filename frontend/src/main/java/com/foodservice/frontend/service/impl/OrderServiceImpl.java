package com.foodservice.frontend.service.impl;

import com.foodservice.frontend.entity.dto.ApiResponseDTO;
import com.foodservice.frontend.entity.dto.OrderCustomerDTO;
import com.foodservice.frontend.entity.dto.OrderWithItemDTO;
import com.foodservice.frontend.service.OrderService;
import com.foodservice.frontend.util.ApiGetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final WebClient webClient;
    @Override
    public OrderCustomerDTO getOrdersByCustomerId(Integer customerId, Map<String, String> params, String token) {

        ApiGetRequest<OrderCustomerDTO> apiGetRequest = new ApiGetRequest<>(webClient);

        return apiGetRequest.get("/orders/customer/" + customerId, 
                params,
                token, 
                new ParameterizedTypeReference<ApiResponseDTO<OrderCustomerDTO>>() {});
    }

    @Override
    public OrderWithItemDTO getOrderDetailsById(Integer orderId, Map<String, String> params, String token) {

        ApiGetRequest<OrderWithItemDTO> apiGetRequest = new ApiGetRequest<>(webClient);

        return apiGetRequest.get("/orders/detail/"+orderId,
                params,
                token,
                new ParameterizedTypeReference<ApiResponseDTO<OrderWithItemDTO>>() {});

    }
}
