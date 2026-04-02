package com.foodservice.frontend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAnalyticsDTO {

    private Integer totalOrders;
    private Double totalSpend;
    private Double avgOrderValue;
}