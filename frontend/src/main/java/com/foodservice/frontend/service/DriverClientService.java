package com.foodservice.frontend.service;

import java.util.Collections;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.foodservice.frontend.entity.dto.DriverResponseDTO;
import com.foodservice.frontend.entity.dto.ApiResponseDTO;
import com.foodservice.frontend.util.ApiGetRequest;

@Service
@RequiredArgsConstructor
public class DriverClientService {

    private final WebClient webClient;

    // Fetch driver details for a given order
    public DriverResponseDTO getDriverByOrderId(int orderId, String token) {

        ApiGetRequest<DriverResponseDTO> apiGetRequest =
                new ApiGetRequest<>(webClient);

        return apiGetRequest.get(
                "/orders/" + orderId + "/driver",
                Collections.emptyMap(),
                token,
                new ParameterizedTypeReference<ApiResponseDTO<DriverResponseDTO>>() {}
        );
    }
}