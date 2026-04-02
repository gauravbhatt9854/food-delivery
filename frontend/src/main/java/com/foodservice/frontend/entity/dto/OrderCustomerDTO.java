package com.foodservice.frontend.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderCustomerDTO {
    private CustomerDTO customer;
    private OrderDetails orderDetails;
}