package com.foodservice.controller;

import com.foodservice.entity.dto.DeliveryAddressDTO;
import com.foodservice.entity.dto.ResponseDTO;
import com.foodservice.service.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery-address")
@RequiredArgsConstructor
public class DeliveryAddressController {

    private final DeliveryAddressService service;

    @GetMapping("/customers/{customerId}/addresses")
    public ResponseEntity<ResponseDTO> getAddressesByCustomerId(@PathVariable Integer customerId) {
        List<DeliveryAddressDTO> data = service.getAddressesByCustomerId(customerId);

        if (data == null || data.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(404, "No addresses found", null));
        }

        return ResponseEntity.ok(
                new ResponseDTO(200, "Addresses fetched successfully", data)
        );
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<ResponseDTO> getAddressById(@PathVariable Integer addressId) {
        DeliveryAddressDTO data = service.getAddressById(addressId);

        if (data == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(404, "Address not found", null));
        }

        return ResponseEntity.ok(
                new ResponseDTO(200, "Address fetched successfully", data)
        );
    }

    @GetMapping("/customers/{customerId}/addresses/count")
    public ResponseEntity<ResponseDTO> getAddressCount(@PathVariable Integer customerId) {
        int count = service.getAddressCount(customerId);

        return ResponseEntity.ok(
                new ResponseDTO(200, "Address count fetched successfully", count)
        );
    }

    @GetMapping("/addresses/city")
    public ResponseEntity<ResponseDTO> getAddressesByCity(@RequestParam String city) {
        List<DeliveryAddressDTO> data = service.getAddressesByCity(city);

        if (data == null || data.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(404, "No addresses found for given city", null));
        }

        return ResponseEntity.ok(
                new ResponseDTO(200, "Addresses fetched by city successfully", data)
        );
    }

    @GetMapping("/customers/{customerId}/addresses/default")
    public ResponseEntity<ResponseDTO> getDefaultAddress(@PathVariable Integer customerId) {
        DeliveryAddressDTO data = service.getDefaultAddress(customerId);

        if (data == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(404, "Default address not found", null));
        }

        return ResponseEntity.ok(
                new ResponseDTO(200, "Default address fetched successfully", data)
        );
    }
}