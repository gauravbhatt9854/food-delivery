package com.foodservice.frontend.entity.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderCouponDTO {
    private String couponCode;
    private BigDecimal discount;
}