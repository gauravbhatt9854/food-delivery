package com.foodservice.frontend.controller;

import com.foodservice.frontend.entity.dto.OrderCustomerDTO;
import com.foodservice.frontend.entity.dto.OrderWithItemDTO;
import com.foodservice.frontend.entity.dto.RestaurantRevenueDTO;
import com.foodservice.frontend.helper.OrderTotalCost;
import com.foodservice.frontend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
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
                                        @RequestParam Map<String, String> params,
                                        Model model,
                                        @CookieValue(name = "token", required = true) String token) {

        OrderCustomerDTO orderCustomerDTO = orderService.getOrdersByCustomerId(customerId, params, token);

        model.addAttribute("orderCustomerDTO", orderCustomerDTO);

        return "pages/orders";
    }

    @GetMapping("/detail/{orderId}")
    public String getOrderDetailsById(@PathVariable("orderId") Integer orderId,
                                      @RequestParam Map<String, String> params,
                                      Model model,
                                      @CookieValue(name = "token", required = true) String token) {

        OrderWithItemDTO orderWithItemDTO = orderService.getOrderDetailsById(orderId, params, token);

        // Calculate total amount
        BigDecimal totalAmount = OrderTotalCost.calculateTotalCost(orderWithItemDTO);
        model.addAttribute("totalAmount", totalAmount);
        
        model.addAttribute("orderWithItemDTO", orderWithItemDTO);

        return "pages/order-details";
    }

    @GetMapping("/detail")
    public String getSearchPage(Model model, @CookieValue(name = "token", required = true) String token) {

        model.addAttribute("title", "Search");
        return "pages/order-search";

    }

    @GetMapping("/revenue/restaurant")
    public String getRestaurantRevenuePage(@CookieValue(name = "token", required = true) String token) {

        return "pages/restaurant-revenue";
    }

    @GetMapping("/revenue/restaurant/{id}")
    public String getRestaurantRevenue(@PathVariable("id") Integer id,
                                       @RequestParam Map<String, String> params,
                                       Model model,
                                       @CookieValue(name = "token", required = true) String token) {

        RestaurantRevenueDTO restaurantRevenueDTO = orderService.getRestaurantRevenue(id, params, token);

        model.addAttribute("restaurantRevenueDTO", restaurantRevenueDTO);

        return "pages/restaurant-revenue";

    }
}
