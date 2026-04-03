package com.foodservice.frontend.service;

import com.foodservice.frontend.entity.dto.OrderCustomerDTO;
import com.foodservice.frontend.entity.dto.OrderWithItemDTO;
import com.foodservice.frontend.entity.dto.RestaurantRevenueDTO;

import java.util.Map;

public interface OrderService {
    OrderCustomerDTO getOrdersByCustomerId(Integer customerId, Map<String, String> params, String token);

    OrderWithItemDTO getOrderDetailsById(Integer orderId, Map<String, String> params, String token);

    RestaurantRevenueDTO getRestaurantRevenue(Integer id, Map<String, String> params, String token);
}
