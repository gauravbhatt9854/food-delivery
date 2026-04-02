package com.foodservice.frontend.controller;

import com.foodservice.frontend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    public String getAllCustomers(
            @RequestParam Map<String, String> params,
            @CookieValue(name = "token", required = false) String token,
            Model model) {

        model.addAttribute("customers", customerService.getAllCustomers(params, token));
        model.addAttribute("currentPage", Integer.parseInt(params.getOrDefault("page", "0")));
        model.addAttribute("size", Integer.parseInt(params.getOrDefault("size", "5")));
        return "pages/customer/customers";
    }

    @GetMapping("/{id}")
    public String getCustomerById(
            @PathVariable Integer id,
            @RequestParam Map<String, String> params,
            @CookieValue(name = "token", required = false) String token,
            Model model) {

        model.addAttribute("customer", customerService.getCustomerById(id, params, token));
        return "pages/customer/customer-detail";
    }

    @GetMapping("/{id}/addresses")
    public String getAddresses(
            @PathVariable Integer id,
            @RequestParam Map<String, String> params,
            @CookieValue(name = "token", required = false) String token,
            Model model) {

        model.addAttribute("addresses", customerService.getAddresses(id, params, token));
        return "pages/customer/addresses";
    }

    @GetMapping("/{id}/orders")
    public String getOrders(
            @PathVariable Integer id,
            @RequestParam Map<String, String> params,
            @CookieValue(name = "token", required = false) String token,
            Model model) {

        model.addAttribute("orders", customerService.getOrders(id, params, token));
        return "pages/customer/customer-orders";
    }

    @GetMapping("/{id}/analytics")
    public String getAnalytics(
            @PathVariable Integer id,
            @RequestParam Map<String, String> params,
            @CookieValue(name = "token", required = false) String token,
            Model model) {

        model.addAttribute("analytics", customerService.getAnalytics(id, params, token));
        return "pages/customer/analytics";
    }
}