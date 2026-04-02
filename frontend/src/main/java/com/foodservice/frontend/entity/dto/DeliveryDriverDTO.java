package com.foodservice.frontend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryDriverDTO {
    private String driverName;
    private String driverPhone;
    private String driverVehicle;
}
