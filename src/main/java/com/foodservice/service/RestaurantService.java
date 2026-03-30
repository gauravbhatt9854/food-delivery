package com.foodservice.service;

import com.foodservice.entity.dto.RatingResponseDTO;
import com.foodservice.entity.dto.RestaurantResponseDTO;
import org.springframework.data.domain.Page;


public interface RestaurantService {

    Page<RestaurantResponseDTO> getAllRestaurants(Integer page, Integer size);

    Page<RatingResponseDTO> getRestaurantRatings(Integer restaurantId, Integer page, Integer size);
}