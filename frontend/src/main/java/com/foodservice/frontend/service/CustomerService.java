package com.foodservice.frontend.service;

import com.foodservice.frontend.entity.dto.*;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers(int page, int size, String token);

    CustomerDTO getCustomerById(Integer id, String token);

    List<DeliveryAddressDTO> getAddresses(Integer id, String token);

    List<OrderItemDetailDTO> getOrders(Integer id, String token);

    CustomerAnalyticsDTO getAnalytics(Integer id, String token);
}
