package com.foodservice.frontend.service;

import com.foodservice.frontend.entity.dto.*;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers(Map<String , String> params , String token);

    CustomerDTO getCustomerById(Integer id, Map<String, String> params, String token);

    List<DeliveryAddressDTO> getAddresses(Integer id, Map<String, String> params, String token);

    List<OrderItemDetailDTO> getOrders(Integer id, Map<String, String> params, String token);

    CustomerAnalyticsDTO getAnalytics(Integer id, Map<String, String> params, String token);
}
