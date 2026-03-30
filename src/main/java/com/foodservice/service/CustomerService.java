package com.foodservice.service;

import com.foodservice.entity.dto.*;

import java.util.List;

public interface CustomerService {
    CustomerDTO getCustomerById(Integer customerId);

    List<CustomerDTO> getAllCustomers(Integer page , Integer size);

    List<CustomerDTO> getCustomersByCity(String city);

    Integer getAddressCount(Integer customerId);

    CustomerAnalyticsDTO getCustomerAnalytics(Integer customerId);

    List<OrderItemDetailDTO> getOrdersByCustomerId(Integer customerId);

    List<DeliveryAddressDTO> getAddressesByCustomerId(Integer customerId);
}