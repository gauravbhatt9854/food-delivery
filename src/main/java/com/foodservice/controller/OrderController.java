package com.foodservice.controller;

import com.foodservice.entity.Order;
import com.foodservice.entity.dto.*;

import com.foodservice.service.OrderService;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponseDTO> getOrdersByCustomerId(@PathVariable Integer customerId,
                                                                @PageableDefault(page=0, size=5) Pageable pageable,
                                                                @RequestParam(value = "status", required = false) String status
    ) {
        System.out.println(status);
        OrderCustomerPageDTO orderDTO = orderService.getOrdersByCustomerId(customerId, pageable, status);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(200, "customer order details", orderDTO));
    }

    @GetMapping("/detail/{orderId}")
    public ResponseEntity<ApiResponseDTO> getOrderDetailsById(@PathVariable Integer orderId) {
        OrderWithItemDTO orderWithItemDTO = orderService.getOrderDetailsById(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(200, "order detail having id: " + orderId, orderWithItemDTO));
    }

}