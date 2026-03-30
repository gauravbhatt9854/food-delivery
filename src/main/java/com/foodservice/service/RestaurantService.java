package com.foodservice.service;

import com.foodservice.entity.dto.RatingResponseDTO;
import com.foodservice.entity.dto.RestaurantResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RestaurantService {

    Page<RestaurantResponseDTO> getAllRestaurants(Pageable pageable);

    Page<RatingResponseDTO> getRestaurantRatings(Integer restaurantId, Pageable pageable);
}