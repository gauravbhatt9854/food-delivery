package com.foodservice.frontend.controller;

import com.foodservice.frontend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    public String getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @CookieValue(name = "token", required = false) String token,
            Model model) {

        model.addAttribute("customers", customerService.getAllCustomers(page, size, token));
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "pages/customer/customers";
    }

    @GetMapping("/{id}")
    public String getCustomerById(@PathVariable Integer id, Model model ,
                                  @CookieValue(name = "token", required = false) String token) {

        model.addAttribute("customer", customerService.getCustomerById(id , token));
        return "pages/customer/customer-detail";
    }

    @GetMapping("/{id}/addresses")
    public String getAddresses(@PathVariable Integer id, Model model , @CookieValue(name = "token", required = false) String token) {

        model.addAttribute("addresses", customerService.getAddresses(id , token));
        return "pages/customer/addresses";
    }

    @GetMapping("/{id}/orders")
    public String getOrders(@PathVariable Integer id, Model model , @CookieValue(name = "token", required = false) String token) {

        model.addAttribute("orders", customerService.getOrders(id , token));
        return "pages/customer/customer-orders";
    }

    @GetMapping("/{id}/analytics")
    public String getAnalytics(@PathVariable Integer id, Model model , @CookieValue(name = "token", required = false) String token) {

        model.addAttribute("analytics", customerService.getAnalytics(id , token));
        return "pages/customer/analytics";
    }
}