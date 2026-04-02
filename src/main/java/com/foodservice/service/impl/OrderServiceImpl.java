package com.foodservice.service.impl;

import com.foodservice.config.CustomMapper;
import com.foodservice.entity.Customer;
import com.foodservice.entity.Order;
import com.foodservice.entity.OrderItem;
import com.foodservice.entity.dto.*;
import com.foodservice.exception.OrderInvalidRequestException;
import com.foodservice.repository.CustomerRepository;
import com.foodservice.repository.OrderRepository;
import com.foodservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

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
}