package com.foodservice.frontend.entity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderItemDetailDTO {
    private LocalDateTime orderDate;
    private String orderStatus;
    private Integer quantity;
    private String itemName;
    private String itemDescription;
    private BigDecimal itemPrice;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhone;
}