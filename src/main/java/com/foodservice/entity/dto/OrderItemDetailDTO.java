package com.foodservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
