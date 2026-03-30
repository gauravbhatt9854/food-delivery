package com.foodservice.controller;

import com.foodservice.entity.dto.OrderItemDTO;
import com.foodservice.entity.dto.ApiResponseDTO;

import com.foodservice.service.OrderItemService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/{orderItemId}")
    public ResponseEntity<ApiResponseDTO> getOrderItemDetailsById(@PathVariable Integer orderItemId) {

        OrderItemDTO orderItemDTO = orderItemService.getOrderItemById(orderItemId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(200, "order item detail having id: " + orderItemId, orderItemDTO));
    }

}
