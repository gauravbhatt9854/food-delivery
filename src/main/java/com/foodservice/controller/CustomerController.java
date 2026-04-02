package com.foodservice.controller;
import com.foodservice.entity.dto.*;
import com.foodservice.service.CustomerService;
import com.foodservice.constants.CustomerConstant;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponseDTO> getCustomerById(@PathVariable Integer customerId) {

        log.info("Received request to fetch customer details. customerId={}", customerId);

        CustomerDTO customer = customerService.getCustomerById(customerId);

        log.debug("Successfully fetched customer details. customerId={}, customerData={}", customerId, customer);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        CustomerConstant.STATUS_200,
                        CustomerConstant.MESSAGE_CUSTOMER_FETCHED,
                        customer
                ));
    }


    @GetMapping("/{customerId}/addresses")
    public ResponseEntity<ApiResponseDTO> getAddressesByCustomerId(@PathVariable Integer customerId) {

        log.info("Received request to fetch delivery addresses for customer. customerId={}", customerId);

        List<DeliveryAddressDTO> addresses = customerService.getAddressesByCustomerId(customerId);

        log.debug("Successfully fetched delivery addresses. customerId={}, totalAddressesCount={}", customerId, addresses.size());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        CustomerConstant.STATUS_200,
                        CustomerConstant.MESSAGE_ADDRESSES_FETCHED,
                        addresses
                ));
    }


    @GetMapping("/{customerId}/orders")
    public ResponseEntity<ApiResponseDTO> getOrdersByCustomerId(@PathVariable Integer customerId) {

        log.info("Received request to fetch orders for customer. customerId={}", customerId);

        List<OrderItemDetailDTO> orders = customerService.getOrdersByCustomerId(customerId);

        log.debug("Successfully fetched orders for customer. customerId={}, ordersData={}", customerId, orders);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        CustomerConstant.STATUS_200,
                        CustomerConstant.MESSAGE_CUSTOMER_ORDERS_FETCHED,
                        orders
                ));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO> getAllCustomers(
            @RequestParam(name = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(name = "size", defaultValue = "5") @Min(1) Integer size
    ) {

        log.info("Received request to fetch all customers");

        List<CustomerDTO> customers = customerService.getAllCustomers(page , size);

        log.debug("Successfully fetched all customers. totalCustomersCount={}", customers.size());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        CustomerConstant.STATUS_200,
                        CustomerConstant.MESSAGE_CUSTOMERS_FETCHED,
                        customers
                ));
    }

    @GetMapping("/city")
    public ResponseEntity<ApiResponseDTO> getCustomersByCity(@RequestParam String city) {

        log.info("Received request to fetch customers by city. city={}", city);

        List<CustomerDTO> customersByCity = customerService.getCustomersByCity(city);

        log.debug("Successfully fetched customers by city. city={}, resultCount={}", city, customersByCity.size());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        CustomerConstant.STATUS_200,
                        CustomerConstant.MESSAGE_CUSTOMERS_FETCHED_BY_CITY,
                        customersByCity
                ));
    }

    @GetMapping("/{customerId}/address-count")
    public ResponseEntity<ApiResponseDTO> getAddressCount(@PathVariable Integer customerId) {

        log.info("Received request to fetch address count for customer. customerId={}", customerId);

        Integer addressCount = customerService.getAddressCount(customerId);

        log.debug("Successfully fetched address count. customerId={}, addressCount={}", customerId, addressCount);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        CustomerConstant.STATUS_200,
                        CustomerConstant.MESSAGE_ADDRESS_COUNT_FETCHED,
                        addressCount
                ));
    }

    @GetMapping("/{customerId}/analytics")
    public ResponseEntity<ApiResponseDTO> getCustomerAnalytics(@PathVariable Integer customerId) {

        log.info("Received request to fetch customer analytics. customerId={}", customerId);

        CustomerAnalyticsDTO analytics = customerService.getCustomerAnalytics(customerId);

        log.debug("Successfully fetched customer analytics. customerId={}, analyticsData={}", customerId, analytics);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        CustomerConstant.STATUS_200,
                        CustomerConstant.MESSAGE_CUSTOMER_ANALYTICS_FETCHED,
                        analytics
                ));
    }
}