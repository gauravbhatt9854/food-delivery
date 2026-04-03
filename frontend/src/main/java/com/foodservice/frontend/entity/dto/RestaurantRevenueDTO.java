package com.foodservice.frontend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RestaurantRevenueDTO {
    private Integer restaurantId;
    private String restaurantName;
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private BigDecimal averageOrderValue;
}
