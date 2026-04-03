package com.foodservice.frontend.helper;

import com.foodservice.frontend.entity.dto.OrderWithItemDTO;

import java.math.BigDecimal;

public class OrderTotalCost {

    public static BigDecimal calculateTotalCost(OrderWithItemDTO orderWithItemDTO) {
        if (orderWithItemDTO != null && orderWithItemDTO.getOrderItems() != null) {
            return orderWithItemDTO.getOrderItems().stream()
                    .filter(item -> item.getItemPrice() != null && item.getQuantity() != null)
                    .map(item -> item.getItemPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())))
                    .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        }
        return BigDecimal.ZERO;
    }
}
