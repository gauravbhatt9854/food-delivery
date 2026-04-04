package com.foodservice.frontend.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.foodservice.frontend.entity.dto.OrderCouponDTO;
import com.foodservice.frontend.service.CouponClientService;

@Controller
@RequestMapping("/view/orders")
public class CouponViewController {

    private final CouponClientService couponClientService;

    public CouponViewController(CouponClientService couponClientService) {
        this.couponClientService = couponClientService;
    }

    // Load coupons page
    @GetMapping
    public String showPage() {
        return "coupons";
    }

    // Fetch coupons for a given order
    @GetMapping("/coupons")
    public String getCoupons(@RequestParam(required = false) Integer orderId,
                             Model model,
                             @CookieValue(name = "token") String token) {

        if (orderId == null) {
            return "coupons";
        }

        try {
            List<OrderCouponDTO> coupons =
                    couponClientService.getCouponsByOrder(orderId, token);

            model.addAttribute("coupons", coupons);

        } catch (Exception e) {
            model.addAttribute("error", "No coupons found for this order");
        }

        return "coupons";
    }
}