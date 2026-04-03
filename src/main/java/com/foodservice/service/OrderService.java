package com.foodservice.service;

import com.foodservice.entity.Order;
import com.foodservice.entity.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface OrderService {

    OrderCustomerDTO getOrdersByCustomerId(Integer customerId);

    OrderCustomerPageDTO getOrdersByCustomerId(Integer customerId, Pageable pageable, String status);

    OrderWithItemDTO getOrderDetailsById(Integer orderId);

    RestaurantRevenueDTO getRevenueByRestaurantId(Integer restaurantId, LocalDate fromDate, LocalDate toDate);

    DriverResponseDTO getDriverByOrderId(Integer orderId);

}