package com.foodservice.controller;

import com.foodservice.entity.dto.CustomerDTO;
import com.foodservice.entity.dto.ResponseDTO;
import com.foodservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    // Get single customer
    @GetMapping("/{customerId}")
    public ResponseEntity<ResponseDTO> getCustomerById(@PathVariable Integer customerId) {
        CustomerDTO data = service.getCustomerById(customerId);

        if (data == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(404, "Customer not found", null));
        }

        return ResponseEntity.ok(
                new ResponseDTO(200, "Customer fetched successfully", data)
        );
    }

    // Get all customers
    @GetMapping
    public ResponseEntity<ResponseDTO> getAllCustomers() {
        List<CustomerDTO> data = service.getAllCustomers();

        if (data == null || data.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(404, "No customers found", null));
        }

        return ResponseEntity.ok(
                new ResponseDTO(200, "Customers fetched successfully", data)
        );
    }

    // Get customers by city
    @GetMapping("/city")
    public ResponseEntity<ResponseDTO> getCustomersByCity(@RequestParam String city) {
        List<CustomerDTO> data = service.getCustomersByCity(city);

        if (data == null || data.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(404, "No customers found for given city", null));
        }

        return ResponseEntity.ok(
                new ResponseDTO(200, "Customers fetched by city successfully", data)
        );
    }

    // Get address count for a customer
    @GetMapping("/{customerId}/address-count")
    public ResponseEntity<ResponseDTO> getAddressCount(@PathVariable Integer customerId) {
        int count = service.getAddressCount(customerId);

        return ResponseEntity.ok(
                new ResponseDTO(200, "Address count fetched successfully", count)
        );
    }

    // Get customer analytics
    @GetMapping("/{customerId}/analytics")
    public ResponseEntity<ResponseDTO> getCustomerAnalytics(@PathVariable Integer customerId) {
        Object data = service.getCustomerAnalytics(customerId);

        if (data == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(404, "Customer analytics not found", null));
        }

        return ResponseEntity.ok(
                new ResponseDTO(200, "Customer analytics fetched successfully", data)
        );
    }
}