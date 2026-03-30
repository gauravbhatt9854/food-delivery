package com.foodservice.controller;

import com.foodservice.entity.dto.ApiResponseDTO;
import com.foodservice.entity.dto.DeliveryAddressDTO;
import com.foodservice.service.DeliveryAddressService;
import com.foodservice.constants.DeliveryAddressConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery-address")
@RequiredArgsConstructor
@Slf4j
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<ApiResponseDTO> getAddressById(@PathVariable Integer addressId) {

        log.info("Received request to fetch delivery address details. addressId={}", addressId);

        DeliveryAddressDTO address = deliveryAddressService.getAddressById(addressId);

        log.debug("Successfully fetched delivery address details. addressId={}, addressData={}", addressId, address);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        DeliveryAddressConstant.STATUS_200,
                        DeliveryAddressConstant.MESSAGE_ADDRESS_FETCHED,
                        address
                ));
    }

    @GetMapping("/customers/{customerId}/addresses/count")
    public ResponseEntity<ApiResponseDTO> getAddressCount(@PathVariable Integer customerId) {

        log.info("Received request to fetch address count for customer. customerId={}", customerId);

        Integer addressCount = deliveryAddressService.getAddressCount(customerId);

        log.debug("Successfully fetched address count. customerId={}, addressCount={}", customerId, addressCount);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        DeliveryAddressConstant.STATUS_200,
                        DeliveryAddressConstant.MESSAGE_ADDRESS_COUNT_FETCHED,
                        addressCount
                ));
    }

    @GetMapping("/addresses/city")
    public ResponseEntity<ApiResponseDTO> getAddressesByCity(@RequestParam String city) {

        log.info("Received request to fetch delivery addresses by city. city={}", city);

        List<DeliveryAddressDTO> addressesByCity = deliveryAddressService.getAddressesByCity(city);

        log.debug("Successfully fetched delivery addresses by city. city={}, resultCount={}", city, addressesByCity.size());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        DeliveryAddressConstant.STATUS_200,
                        DeliveryAddressConstant.MESSAGE_ADDRESSES_FETCHED_BY_CITY,
                        addressesByCity
                ));
    }

    @GetMapping("/customers/{customerId}/addresses/default")
    public ResponseEntity<ApiResponseDTO> getDefaultAddress(@PathVariable Integer customerId) {

        log.info("Received request to fetch default delivery address. customerId={}", customerId);

        DeliveryAddressDTO defaultAddress = deliveryAddressService.getDefaultAddress(customerId);

        log.debug("Successfully fetched default delivery address. customerId={}, addressData={}", customerId, defaultAddress);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        DeliveryAddressConstant.STATUS_200,
                        DeliveryAddressConstant.MESSAGE_DEFAULT_ADDRESS_FETCHED,
                        defaultAddress
                ));
    }
}