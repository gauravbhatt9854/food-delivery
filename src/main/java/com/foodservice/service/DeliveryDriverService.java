package com.foodservice.service;

import com.foodservice.entity.dto.DeliveryDriverDTO;
import com.foodservice.entity.dto.DeliveryDriverResponseDTO;
import java.util.List;

public interface DeliveryDriverService {

    // Get single driver
    DeliveryDriverResponseDTO getDriverById(Integer driverId);

    // Get all drivers
    List<DeliveryDriverResponseDTO> getAllDrivers();

    // Get driver deliveries (orders handled by driver)
    List<DeliveryDriverResponseDTO> getDriverDeliveries(Integer driverId);

    List<DeliveryDriverResponseDTO> getAllDrivers(String sort);

	List<DeliveryDriverResponseDTO> getOrderByDriver(Integer driverId);

	List<DeliveryDriverResponseDTO> getcustomerByDriver(Integer driverId);

	List<DeliveryDriverResponseDTO> getCustomerOrderByDriver(Integer driverId, Integer customerId);

	List<DeliveryDriverResponseDTO> getRestaurantsByDriver(Integer driverId);
}