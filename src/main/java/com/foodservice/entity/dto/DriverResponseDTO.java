package com.foodservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriverResponseDTO {
    private Integer driverId;
    private String driverName;
    private String driverPhone;
    private String driverVehicle;
}