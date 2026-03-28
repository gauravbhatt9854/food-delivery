package com.foodservice.service;

import com.foodservice.entity.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO getCustomerById(Integer customerId);

    List<CustomerDTO> getAllCustomers();

    List<CustomerDTO> getCustomersByCity(String city);

    Integer getAddressCount(Integer customerId);

    Object getCustomerAnalytics(Integer customerId);
}