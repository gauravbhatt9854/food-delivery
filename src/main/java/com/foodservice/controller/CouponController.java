package com.foodservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foodservice.entity.dto.OrderCouponDTO;
import com.foodservice.entity.dto.ApiResponseDTO;
import com.foodservice.service.CouponService;

@RestController
@RequestMapping("/api/v1/coupons")   //changed the routing
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<ApiResponseDTO> getCoupons(@PathVariable Integer id) {

        List<OrderCouponDTO> coupons = couponService.getCouponsByOrder(id);

        return ResponseEntity.ok(
            new ApiResponseDTO(
                200,
                "Coupons fetched successfully",
                coupons
            )
        );
    }
}