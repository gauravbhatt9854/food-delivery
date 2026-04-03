package com.foodservice.frontend.controller;

import com.foodservice.frontend.entity.dto.RestaurantRatingsDTO;
import com.foodservice.frontend.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RepositoryController {
    private final RestaurantService restaurantService;

    @GetMapping("/ratings")
    public String getRestaurantRevenuePage(@CookieValue(name = "token", required = true) String token) {
        return "pages/restaurant-ratings";

    }

    @GetMapping("/ratings/{id}")
    public String getRestaurantRatings(
            @PathVariable("id") Integer restaurantId,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String customerName,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @CookieValue(value = "token", required = true) String token,
            Model model) {

        try {
            System.out.println("API HITTED");
            log.info("Fetching ratings for restaurant ID: {} with filters", restaurantId);

            Map<String, String> params = new HashMap<>();
            if (rating != null) params.put("rating", rating.toString());
            if (minRating != null) params.put("minRating", minRating.toString());
            if (fromDate != null) params.put("fromDate", fromDate);
            if (toDate != null) params.put("toDate", toDate);
            if (keyword != null) params.put("keyword", keyword);
            if (customerName != null) params.put("customerName", customerName);
            params.put("page", page.toString());
            params.put("size", size.toString());

            RestaurantRatingsDTO ratings = restaurantService.getRestaurantRatings(restaurantId, params, token);

            model.addAttribute("restaurantId", restaurantId);
            model.addAttribute("restaurantRatingsDTO", ratings);
            model.addAttribute("rating", rating);
            model.addAttribute("minRating", minRating);
            model.addAttribute("fromDate", fromDate);
            model.addAttribute("toDate", toDate);
            model.addAttribute("keyword", keyword);
            model.addAttribute("customerName", customerName);

            return "pages/restaurant-ratings";
            
        } catch (Exception e) {
            log.error("Error fetching ratings for restaurant ID: {}", restaurantId, e);
            model.addAttribute("error", "Failed to load ratings: " + e.getMessage());
            return "error";
        }
    }






}
