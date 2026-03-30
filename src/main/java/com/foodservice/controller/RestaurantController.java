package com.foodservice.controller;

import com.foodservice.entity.dto.ApiResponseDTO;
import com.foodservice.entity.dto.MenuItemResponseDTO;
import com.foodservice.entity.dto.RatingResponseDTO;
import com.foodservice.entity.dto.RestaurantResponseDTO;
import com.foodservice.service.RestaurantService;
import com.foodservice.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;

    @GetMapping("/fetch")
    public ResponseEntity<ApiResponseDTO> fetchRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Fetching all restaurants with pagination - Page: {}, Size: {}", page, size);
        Page<RestaurantResponseDTO> restaurantList = restaurantService.getAllRestaurants(page, size);

        log.info("Fetched {} restaurants", restaurantList.getNumberOfElements());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(200, "fetched " + restaurantList.getNumberOfElements() + " restaurants", restaurantList));
    }

    @GetMapping("/{id}/ratings")
    public ResponseEntity<ApiResponseDTO> fetchRestaurantRatings(
            @PathVariable("id") Integer restaurantId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){

        log.info("Fetching ratings for restaurant ID: {} - Page: {}, Size: {}", restaurantId, page, size);

        Page<RatingResponseDTO> ratingsList = restaurantService.getRestaurantRatings(restaurantId, page, size);

        log.info("Fetched {} ratings for restaurant ID: {}", ratingsList.getNumberOfElements(), restaurantId);
        return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseDTO(200, "fetched " + ratingsList.getNumberOfElements() + " ratings for restaurant having id: " + restaurantId, ratingsList));
    }
}
