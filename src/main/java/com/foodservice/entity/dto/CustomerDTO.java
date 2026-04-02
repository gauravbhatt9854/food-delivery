package com.foodservice.entity.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Integer customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
}