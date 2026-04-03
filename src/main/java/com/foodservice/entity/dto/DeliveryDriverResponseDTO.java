package com.foodservice.entity.dto;

import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDriverResponseDTO {
    private Integer driverId;
    private String driverName;
    private String driverPhone;
    private String driverVehicle;

    private Integer orderId;
    private String resturentName;
    private Integer resturentId;
	private Integer customerId;
	private String customerName;
    private String orderStatus;
    
    
	public DeliveryDriverResponseDTO() {
		super();
		this.driverId = driverId;
		this.driverName = driverName;
		this.driverPhone = driverPhone;
		this.driverVehicle = driverVehicle;
		this.orderId = orderId;
		this.resturentId = resturentId;
		this.customerId = customerId;
		this.customerName= customerName;
		this.resturentName = resturentName;
		this.orderStatus = orderStatus;
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
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getResturentName() {
		return resturentName;
	}
	public void setResturentName(String resturentName) {
		this.resturentName = resturentName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getResturentId() {
		return resturentId;
	}
	public void setResturentId(Integer resturentId) {
		this.resturentId = resturentId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
