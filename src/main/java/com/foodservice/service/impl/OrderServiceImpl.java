package com.foodservice.service.impl;

import com.foodservice.entity.Order;
import com.foodservice.entity.dto.OrderDTO;
import com.foodservice.entity.dto.OrderItemDetailDTO;
import com.foodservice.repository.OrderRepository;
import com.foodservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public OrderDTO getOrderDetailsById(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        List<OrderItemDetailDTO> orderDetails = orderRepository.getOrderDetailsByOrderId(orderId);
        if (order == null) return null;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setCustomer(order.getCustomer());
        orderDTO.setRestaurant(order.getRestaurant());
        orderDTO.setDeliveryDriver(order.getDeliveryDriver());
        orderDTO.setOrderItems(orderDetails);
        return orderDTO;
    }

    @Override
    public OrderDTO getOrdersByCustomerId(Integer customerId) {
        List<OrderItemDetailDTO> orderDetails = orderRepository.getOrderDetailsByCustomerId(customerId);
        return null;
    }
}
