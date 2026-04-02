package com.foodservice.frontend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ItemWithQuantity {
    private Integer quantity;
    private String itemName;
    private String itemDescription;
    private BigDecimal itemPrice;
}
