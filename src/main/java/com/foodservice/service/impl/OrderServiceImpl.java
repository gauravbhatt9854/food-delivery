package com.foodservice.service.impl;

import com.foodservice.config.CustomMapper;
import com.foodservice.entity.Customer;
import com.foodservice.entity.Order;
import com.foodservice.entity.OrderItem;
import com.foodservice.entity.dto.*;
import com.foodservice.exception.InvalidDateRangeException;
import com.foodservice.exception.OrderInvalidRequestException;
import com.foodservice.exception.ResourceNotFoundException;
import com.foodservice.repository.CustomerRepository;
import com.foodservice.repository.OrderRepository;
import com.foodservice.repository.RestaurantRepository;
import com.foodservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public OrderCustomerDTO getOrdersByCustomerId(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new OrderInvalidRequestException("Customer do next exist having customer id: " + customerId));

        List<OrderItemDetailDTO> orderDetails = orderRepository.getOrderDetailsByCustomerId(customerId);

        return new OrderCustomerDTO(CustomMapper.customerToCustomerDTO(customer), orderDetails);
    }

    @Override
    public OrderCustomerPageDTO getOrdersByCustomerId(Integer customerId, Pageable pageable, String status) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new OrderInvalidRequestException("Customer do next exist having customer id: " + customerId));

        Page<OrderItemDetailDTO> orderDetails = orderRepository.getOrderDetailsByCustomerId(customerId, pageable, status);

        return new OrderCustomerPageDTO(CustomMapper.customerToCustomerDTO(customer), orderDetails);
    }

    @Override
    public OrderWithItemDTO getOrderDetailsById(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderInvalidRequestException("Order not found having order id: " + orderId));

        List<ItemWithQuantity> itemWithQuantity = orderRepository.getOrderItemWithQuantityById(orderId);

        return CustomMapper.orderToOrderWithItemDTO(order, new OrderWithItemDTO(), itemWithQuantity);
    }

    @Override
    public RestaurantRevenueDTO getRevenueByRestaurantId(
            Integer restaurantId,
            LocalDate fromDate,
            LocalDate toDate) {

        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException(
                    "Restaurant not found with ID: " + restaurantId);
        }

        // Validate date range
        if (fromDate != null && toDate != null) {
            if (fromDate.isAfter(toDate)) {
                throw new InvalidDateRangeException(
                        "From date cannot be after to date. From: " + fromDate + ", To: " + toDate);
            }
        }

        // Validate dates are not in the future
        LocalDate today = LocalDate.now();
        if (fromDate != null && fromDate.isAfter(today)) {
            throw new InvalidDateRangeException(
                    "From date cannot be in the future. From: " + fromDate + ", Today: " + today);
        }
        if (toDate != null && toDate.isAfter(today)) {
            throw new InvalidDateRangeException(
                    "To date cannot be in the future. To: " + toDate + ", Today: " + today);
        }

        LocalDateTime from = (fromDate != null) ? fromDate.atStartOfDay()   : null;
        LocalDateTime to   = (toDate   != null) ? toDate.atTime(23, 59, 59) : null;

        RestaurantRevenueDTO revenue =
                orderRepository.getRevenueByRestaurantId(restaurantId, from, to);

        if (revenue == null || revenue.getTotalOrders() == 0) {
            throw new ResourceNotFoundException(
                    "No orders found for restaurant ID: " + restaurantId);
        }

        return revenue;
    }
}