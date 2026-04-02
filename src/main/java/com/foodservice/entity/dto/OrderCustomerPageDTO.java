package com.foodservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
public class OrderCustomerPageDTO {
    private CustomerDTO customer;
    private Page<OrderItemDetailDTO> orderDetails;
}
