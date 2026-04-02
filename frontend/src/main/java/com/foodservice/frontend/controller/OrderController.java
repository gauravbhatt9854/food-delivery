package com.foodservice.frontend.controller;

import com.foodservice.frontend.entity.dto.OrderCustomerDTO;
import com.foodservice.frontend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/customer")
    public String getCustomerHome(Model model, @CookieValue(name = "token", required = true) String token) {

        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "Click on the top right button to view your orders details");

        return "pages/order-customer-home";
    }

    @GetMapping("/customer/{customerId}")
    public String getOrdersByCustomerId(@PathVariable("customerId") Integer customerId,
                                        Model model,
                                        @RequestParam Map<String, String> params,
                                        @CookieValue(name = "token", required = true) String token
    ) {

        OrderCustomerDTO orderCustomerDTO = orderService.getOrdersByCustomerId(customerId, params, token);

        model.addAttribute("orderCustomerDTO", orderCustomerDTO);
        return "pages/orders";
    }

    @GetMapping("/detail/{orderId}")
    public String getOrderDetailsById() {

    }
}
