package com.foodservice.frontend.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderWithItemDTO {
    private CustomerDTO customer;
    private RestaurantResponseDTO restaurant;
    private DeliveryDriverDTO deliveryDriver;
    private LocalDateTime orderDate;
    private String orderStatus;
    private List<ItemWithQuantity> orderItems;

}
