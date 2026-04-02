package com.foodservice.frontend.entity.dto;

import lombok.Data;

@Data
public class DeliveryAddressDTO {

    private Integer id;
    private Integer customerId;

    private String addressLine1;
    private String addressLine2;

    private String city;
    private String state;
    private String postalCode;
}
