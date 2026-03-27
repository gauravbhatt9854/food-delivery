package com.foodservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DeliveryDrivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Integer driverId;

    @Column(name = "driver_name", nullable = false)
    private String driverName;

    @Column(name = "driver_phone", nullable = false)
    private String driverPhone;

    @Column(name = "driver_vehicle")
    private String driverVehicle;
}