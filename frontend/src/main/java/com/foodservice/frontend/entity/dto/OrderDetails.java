package com.foodservice.frontend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderDetails {
    private List<OrderItemDetailDTO> content;
    private Page page;
}
