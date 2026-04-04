package com.foodservice.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.foodservice.frontend.entity.dto.DriverResponseDTO;
import com.foodservice.frontend.service.DriverClientService;

@Controller
@RequestMapping("/view/driver")
public class DriverViewController {

    private final DriverClientService driverClientService;

    public DriverViewController(DriverClientService driverClientService) {
        this.driverClientService = driverClientService;
    }

    // Show driver page
    @GetMapping
    public String showPage() {
        return "driver";
    }

    // Fetch driver by orderId
    @GetMapping("/order")
    public String getDriver(@RequestParam (required = false) Integer orderId,
                            Model model,
                            @CookieValue(name = "token", required = true) String token) {

        if (orderId == null) {
            return "driver"; // just show empty page
        }

        DriverResponseDTO driver =
                driverClientService.getDriverByOrderId(orderId, token);

        model.addAttribute("driver", driver);

        return "driver";
    }
}