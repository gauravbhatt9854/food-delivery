package com.foodservice.service;

import com.foodservice.entity.Order;
import com.foodservice.entity.dto.OrderCustomerDTO;
import com.foodservice.entity.dto.OrderDTO;

public interface OrderService {
    OrderDTO getOrderDetailsById(Integer orderId);

    OrderCustomerDTO getOrdersByCustomerId(Integer customerId);
}
