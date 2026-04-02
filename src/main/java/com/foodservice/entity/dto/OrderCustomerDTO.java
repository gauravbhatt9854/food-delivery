package com.foodservice.entity.dto;

import com.foodservice.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderCustomerDTO {
    private CustomerDTO customer;
    private List<OrderItemDetailDTO> orderItems;
}
