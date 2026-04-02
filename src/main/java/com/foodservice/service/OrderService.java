package com.foodservice.service;

import com.foodservice.entity.Order;
import com.foodservice.entity.dto.OrderCustomerDTO;
import com.foodservice.entity.dto.OrderCustomerPageDTO;
import com.foodservice.entity.dto.OrderDTO;
import com.foodservice.entity.dto.OrderWithItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderCustomerDTO getOrdersByCustomerId(Integer customerId);

    OrderCustomerPageDTO getOrdersByCustomerId(Integer customerId, Pageable pageable, String status);

    OrderWithItemDTO getOrderDetailsById(Integer orderId);

}