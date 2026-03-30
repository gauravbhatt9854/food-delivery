package com.foodservice.service.impl;

import com.foodservice.entity.dto.RatingResponseDTO;
import com.foodservice.entity.dto.RestaurantResponseDTO;
import com.foodservice.config.CustomMapper;
import com.foodservice.exception.RestaurantNotFoundException;
import com.foodservice.repository.RatingRepository;
import com.foodservice.repository.RestaurantRepository;
import com.foodservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RatingRepository ratingRepository;

    @Override
    public Page<RestaurantResponseDTO> getAllRestaurants(Integer page, Integer size) {
        log.info("Fetching restaurants with pagination - Page: {}, Size: {}", page, size);

        return restaurantRepository.findAll(PageRequest.of(page, size))
                .map(CustomMapper::toRestaurantDto);
    }

    @Override
    public Page<RatingResponseDTO> getRestaurantRatings(Integer restaurantId, Integer page, Integer size) {
        log.info("Fetching ratings for restaurant ID: {} with pagination - Page: {}, Size: {}", restaurantId, page, size);

        if (!restaurantRepository.existsById(restaurantId)) {
            log.error("Failed to fetch ratings. Restaurant ID {} not found.", restaurantId);
            throw new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId);
        }

        return ratingRepository.findByRestaurant_RestaurantId(restaurantId, PageRequest.of(page, size))
                .map(CustomMapper::toRatingDto);
    }

}