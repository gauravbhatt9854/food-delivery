package com.foodservice.service;

import com.foodservice.entity.Order;
import com.foodservice.entity.dto.OrderDTO;

public interface OrderService {
    OrderDTO getOrderDetailsById(Integer orderId);

    OrderDTO getOrdersByCustomerId(Integer customerId);
}
