package com.foodservice.frontend.service.impl;

import com.foodservice.frontend.entity.dto.ApiResponseDTO;
import com.foodservice.frontend.entity.dto.RestaurantRatingsDTO;
import com.foodservice.frontend.service.RestaurantService;
import com.foodservice.frontend.util.ApiGetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final WebClient webClient;

    @Override
    public RestaurantRatingsDTO getRestaurantRatings(Integer id, Map<String, String> params, String token) {

        ApiGetRequest<RestaurantRatingsDTO> apiGetRequest = new ApiGetRequest<>(webClient);

        String url = "/restaurants/ratings/" + id;

        ParameterizedTypeReference<ApiResponseDTO<RestaurantRatingsDTO>> typeRef =
                new ParameterizedTypeReference<>() {};

        return apiGetRequest.get(url, params, token, typeRef);
    }
}