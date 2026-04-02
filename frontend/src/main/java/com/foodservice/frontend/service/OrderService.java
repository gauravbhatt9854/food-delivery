package com.foodservice.frontend.service;

import com.foodservice.frontend.entity.dto.OrderCustomerDTO;

import java.util.Map;

public interface OrderService {
    OrderCustomerDTO getOrdersByCustomerId(Integer customerId, Map<String, String> params, String token);
}
