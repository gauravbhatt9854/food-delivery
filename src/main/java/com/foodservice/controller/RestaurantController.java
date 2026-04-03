package com.foodservice.controller;

import com.foodservice.constants.RestaurantConstants;
import com.foodservice.entity.dto.*;
import com.foodservice.service.RatingService;
import com.foodservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Slf4j
@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/fetch")
    public ResponseEntity<ApiResponseDTO> fetchRestaurants(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        log.info("Fetching all restaurants with pagination - Page: {}, Size: {}", page, size);
        Page<RestaurantResponseDTO> restaurantList = restaurantService.getAllRestaurants(page, size);

        log.info("Fetched {} restaurants", restaurantList.getNumberOfElements());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        RestaurantConstants.STATUS_200,
                        RestaurantConstants.MESSAGE_FETCH_SUCCESS,
                        restaurantList));
    }

    @GetMapping("/ratings/{id}")
    public ResponseEntity<ApiResponseDTO> fetchRestaurantRatings(
            @PathVariable("id") Integer restaurantId,
            @RequestParam(defaultValue = "0")  Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String customerName) {

        RatingFilterDTO filter = new RatingFilterDTO(
                rating,
                minRating,
                fromDate != null ? fromDate.atStartOfDay()   : null,
                toDate   != null ? toDate.atTime(23, 59, 59) : null,
                keyword,
                customerName
        );

        Page<RatingResponseDTO> ratingsList =
                restaurantService.getRestaurantRatings(restaurantId, filter, page, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        RestaurantConstants.STATUS_200,
                        RestaurantConstants.MESSAGE_RATINGS_FETCH_SUCCESS,
                        ratingsList));
    }


    //top rating
    private final RatingService ratingService;

    @GetMapping("/top-rated")
    public ResponseEntity<ApiResponseDTO> getTopRatedRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        Page<TopRatedRestaurantDTO> data =
                ratingService.getTopRatedRestaurants(page, size);

        return ResponseEntity.ok(
                new ApiResponseDTO(
                        200,
                        "Top rated restaurants fetched successfully",
                        data
                )
        );
    }
}