package com.foodservice.controller;

import com.foodservice.entity.dto.DeliveryDriverResponseDTO;
import com.foodservice.entity.dto.ApiResponseDTO;
import com.foodservice.service.DeliveryDriverService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/drivers")
//@RequiredArgsConstructor
//@Slf4j
public class DeliveryDriverController {

    private final DeliveryDriverService deliveryDriverservice;
    
//    Logger log = LoggerFactory.getLogger(DeliveryDriverController.class);
//    private static final Logger log = LoggerFactory.getLogger(DeliveryDriverController.class);
    
    public DeliveryDriverController(DeliveryDriverService deliveryDriverservice){
		this.deliveryDriverservice = deliveryDriverservice; 
	}

    // ---------------- Get Driver by ID --------------
    @GetMapping(value = "/{driverId}", produces = "application/json")
    public ResponseEntity<ApiResponseDTO> getDriverById(@PathVariable Integer driverId) {
//    	log.info("API called: Get driver by ID {}", driverId);
    	
        DeliveryDriverResponseDTO driver = deliveryDriverservice.getDriverById(driverId);

//        log.info("API Response for driver by ID {}", driverId);
        
        return ResponseEntity.ok(
                new ApiResponseDTO(200, "Driver fetched successfully", driver)
        );
    }

    // ---------- Get All Drivers ------------
    @GetMapping(produces = "application/json")
    public ResponseEntity<ApiResponseDTO> getAllDrivers() {
        List<DeliveryDriverResponseDTO> drivers = deliveryDriverservice.getAllDrivers();
        return ResponseEntity
                .ok()
                .header("Content-Type", "application/json")
                .body(new ApiResponseDTO(200, "Drivers fetched successfully", drivers));
    }

    // --------------- Get Driver Deliveries ----------------
    @GetMapping("/{driverId}/deliveries")
    public ResponseEntity<ApiResponseDTO> getDriverDeliveries(@PathVariable Integer driverId) {
        List<DeliveryDriverResponseDTO> list = deliveryDriverservice.getDriverDeliveries(driverId);
        return ResponseEntity.ok(
            new ApiResponseDTO(200, "Driver deliveries fetched", list)
        );
    }
    
    // ---------- Get All Drivers with sorting ------------
	@GetMapping(value = "/sorted", produces = "application/json")
    public ResponseEntity<ApiResponseDTO> getAllDrivers(@RequestParam(required = false, defaultValue="asc")String sort){
        	List<DeliveryDriverResponseDTO> drivers = deliveryDriverservice.getAllDrivers(sort);
        	return ResponseEntity.status(200).header("Content-Type", "application/json").body(new ApiResponseDTO(200, "Drivers fetched successfully", drivers));
        }
        
      // ---------- Get all orders assigned to driver ------------
     @GetMapping("/{driverId}/orders")
     public ResponseEntity<ApiResponseDTO> getOrderByDriver(@PathVariable Integer driverId){
    	 List<DeliveryDriverResponseDTO> list = deliveryDriverservice.getOrderByDriver(driverId);
    	 return ResponseEntity.status(200).header("Content-Type", "application/json").body(new ApiResponseDTO(200, "Orders fetched successfully", list));
     }
     
     // --------- Get all customers served by driver ----------
     @GetMapping("/{driverId}/customers")
     public ResponseEntity<ApiResponseDTO> getCustomerByDriver(@PathVariable Integer driverId){
    	 List<DeliveryDriverResponseDTO> list = deliveryDriverservice.getcustomerByDriver(driverId);
    	 return ResponseEntity.status(200).header("Content-Type", "application/json").body(new ApiResponseDTO(200, "Customers fetched successfully", list));
     }
     
     // -------- Get specific customer orders handled by driver ---------
     @GetMapping("/{driverId}/customers/{customerId}/orders")
     public ResponseEntity<ApiResponseDTO> getCustomerOrderByDriver(@PathVariable Integer driverId, @PathVariable Integer customerId){
    	 List<DeliveryDriverResponseDTO> list = deliveryDriverservice.getCustomerOrderByDriver(driverId, customerId);
		 return ResponseEntity.status(200).header("Content-Type", "application/json").body(new ApiResponseDTO(200, "Customer orders fetched successfully", list));
		 
     }
     
     // -------- Get restaurants a driver has delivered from ---------
     @GetMapping("/{driverId}/restaurants")
     public ResponseEntity<ApiResponseDTO> getRestaurantsByDriver(@PathVariable Integer driverId){
		 List<DeliveryDriverResponseDTO> list = deliveryDriverservice.getRestaurantsByDriver(driverId);
		 return ResponseEntity.status(200).header("Content-Type", "application/json").body(new ApiResponseDTO(200, "Restaurants fetched successfully", list));
	 }
     
     //--------- Total orders delivered -----------
     @GetMapping("/{driverId}/total-orders")
     public ResponseEntity<ApiResponseDTO> getToalOrderByDriver(@PathVariable Integer driverId){
    	 		 List<DeliveryDriverResponseDTO> list = deliveryDriverservice.getOrderByDriver(driverId);
    	 		 return ResponseEntity.status(200)
    	 				 .header("Content-Type", "application/json")
    	 				 .body(new ApiResponseDTO(200, "Total orders fetched successfully", list.size()));
     }
}