package com.foodservice.entity.dto;

import com.foodservice.entity.Customer;
import lombok.Data;

import java.util.List;

@Data
public class OrderCustomerDTO {
    private Customer customer;
    private List<OrderItemDetailDTO> orderItems;
}
