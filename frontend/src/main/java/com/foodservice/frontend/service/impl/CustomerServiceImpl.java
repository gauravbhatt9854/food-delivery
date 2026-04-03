package com.foodservice.frontend.service.impl;

import com.foodservice.frontend.entity.dto.*;
import com.foodservice.frontend.service.CustomerService;
import com.foodservice.frontend.util.ApiGetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final WebClient webClient;

    @Override
    public List<CustomerDTO> getAllCustomers(Map<String , String> params , String token) {

        ApiGetRequest<List<CustomerDTO>> apiGetRequest = new ApiGetRequest<>(webClient);

        String url = "/customers";

        ParameterizedTypeReference<ApiResponseDTO<List<CustomerDTO>>> typeRef =
                new ParameterizedTypeReference<>() {};

        return apiGetRequest.get(url, params, token, typeRef);
    }

    @Override
    public CustomerDTO getCustomerById(Integer id, Map<String, String> params, String token) {

        ApiGetRequest<CustomerDTO> apiGetRequest = new ApiGetRequest<>(webClient);

        String url = "/customers/" + id;

        ParameterizedTypeReference<ApiResponseDTO<CustomerDTO>> typeRef =
                new ParameterizedTypeReference<>() {};

        return apiGetRequest.get(url, params, token, typeRef);
    }

    @Override
    public List<DeliveryAddressDTO> getAddresses(Integer id, Map<String, String> params, String token) {

        ApiGetRequest<List<DeliveryAddressDTO>> apiGetRequest = new ApiGetRequest<>(webClient);

        String url = "/customers/" + id + "/addresses";

        ParameterizedTypeReference<ApiResponseDTO<List<DeliveryAddressDTO>>> typeRef =
                new ParameterizedTypeReference<>() {};

        return apiGetRequest.get(url, params, token, typeRef);
    }

    @Override
    public List<OrderItemDetailDTO> getOrders(Integer id, Map<String, String> params, String token) {

        ApiGetRequest<List<OrderItemDetailDTO>> apiGetRequest = new ApiGetRequest<>(webClient);

        String url = "/customers/" + id + "/orders";

        ParameterizedTypeReference<ApiResponseDTO<List<OrderItemDetailDTO>>> typeRef =
                new ParameterizedTypeReference<>() {};

        return apiGetRequest.get(url, params, token, typeRef);
    }

    @Override
    public CustomerAnalyticsDTO getAnalytics(Integer id, Map<String, String> params, String token) {

        ApiGetRequest<CustomerAnalyticsDTO> apiGetRequest = new ApiGetRequest<>(webClient);

        String url = "/customers/" + id + "/analytics";

        ParameterizedTypeReference<ApiResponseDTO<CustomerAnalyticsDTO>> typeRef =
                new ParameterizedTypeReference<>() {};

        return apiGetRequest.get(url, params, token, typeRef);
    }

    @Override
    public List<CustomerDTO> getCustomerByCiy(Map<String, String> params, String token) {
        ApiGetRequest<List<CustomerDTO>> apiGetRequest = new ApiGetRequest<>(webClient);

        String url = "/customers/city";

        ParameterizedTypeReference<ApiResponseDTO<List<CustomerDTO>>> typeRef =
                new ParameterizedTypeReference<>() {};

        return apiGetRequest.get(url, params, token, typeRef);
    }
}