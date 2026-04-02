package com.foodservice.frontend.service.impl;

import com.foodservice.frontend.entity.dto.ApiResponseDTO;
import com.foodservice.frontend.entity.dto.DeliveryAddressDTO;
import com.foodservice.frontend.service.DeliveryAddressService;
import com.foodservice.frontend.util.ApiGetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final WebClient webClient;

    @Override
    public DeliveryAddressDTO getAddressById(Integer addressId, Map<String, String> params, String token) {

        ApiGetRequest<DeliveryAddressDTO> apiGetRequest = new ApiGetRequest<>(webClient);

        String url = "/delivery-address/addresses/" + addressId;

        ParameterizedTypeReference<ApiResponseDTO<DeliveryAddressDTO>> typeRef =
                new ParameterizedTypeReference<>() {};

        return apiGetRequest.get(url, params, token, typeRef);
    }

    @Override
    public List<DeliveryAddressDTO> getAddressesByCity(Map<String , String> params, String token) {

        ApiGetRequest<List<DeliveryAddressDTO>> apiGetRequest = new ApiGetRequest<>(webClient);

        String url = "/delivery-address/addresses/city";

        ParameterizedTypeReference<ApiResponseDTO<List<DeliveryAddressDTO>>> typeRef =
                new ParameterizedTypeReference<>() {};

        return apiGetRequest.get(url, params, token, typeRef);
    }
}