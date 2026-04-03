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
    
    public DeliveryDriver(){}

	public DeliveryDriver(Integer driverId, String driverName, String driverPhone, String driverVehicle) {
		super();
		this.driverId = driverId;
		this.driverName = driverName;
		this.driverPhone = driverPhone;
		this.driverVehicle = driverVehicle;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getDriverVehicle() {
		return driverVehicle;
	}

	public void setDriverVehicle(String driverVehicle) {
		this.driverVehicle = driverVehicle;
	}
    
	
    
}