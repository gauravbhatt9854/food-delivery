package com.foodservice.controller;

import com.foodservice.entity.dto.ResponseDTO;
import com.foodservice.service.DeliveryAddressService;
import com.foodservice.constants.DeliveryAddressConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/delivery-address")
@RequiredArgsConstructor
public class DeliveryAddressController {

    private final DeliveryAddressService service;

    @GetMapping("/customers/{customerId}/addresses")
    public ResponseEntity<ResponseDTO> getAddressesByCustomerId(@PathVariable Integer customerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(
                        DeliveryAddressConstant.STATUS_200,
                        DeliveryAddressConstant.MESSAGE_ADDRESSES_FETCHED,
                        service.getAddressesByCustomerId(customerId)
                ));
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<ResponseDTO> getAddressById(@PathVariable Integer addressId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(
                        DeliveryAddressConstant.STATUS_200,
                        DeliveryAddressConstant.MESSAGE_ADDRESS_FETCHED,
                        service.getAddressById(addressId)
                ));
    }

    @GetMapping("/customers/{customerId}/addresses/count")
    public ResponseEntity<ResponseDTO> getAddressCount(@PathVariable Integer customerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(
                        DeliveryAddressConstant.STATUS_200,
                        DeliveryAddressConstant.MESSAGE_ADDRESS_COUNT_FETCHED,
                        service.getAddressCount(customerId)
                ));
    }

    @GetMapping("/addresses/city")
    public ResponseEntity<ResponseDTO> getAddressesByCity(@RequestParam String city) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(
                        DeliveryAddressConstant.STATUS_200,
                        DeliveryAddressConstant.MESSAGE_ADDRESSES_FETCHED_BY_CITY,
                        service.getAddressesByCity(city)
                ));
    }

    @GetMapping("/customers/{customerId}/addresses/default")
    public ResponseEntity<ResponseDTO> getDefaultAddress(@PathVariable Integer customerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(
                        DeliveryAddressConstant.STATUS_200,
                        DeliveryAddressConstant.MESSAGE_DEFAULT_ADDRESS_FETCHED,
                        service.getDefaultAddress(customerId)
                ));
    }
}