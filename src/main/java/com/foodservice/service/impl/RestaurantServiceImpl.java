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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RatingRepository ratingRepository;

//    @Override
//    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO requestDTO) {
//        log.info("Creating new restaurant: {}", requestDTO.getRestaurantName());
//
//        // Basic check to prevent duplicate phone numbers
//        if (restaurantRepository.existsByRestaurantPhone(requestDTO.getRestaurantPhone())) {
//            log.error("Restaurant creation failed. Phone number already exists: {}", requestDTO.getRestaurantPhone());
//            throw new RuntimeException("Phone number already in use");
//        }
//
//        Restaurant restaurant = customMapper.toRestaurantEntity(requestDTO);
//        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
//
//        log.info("Successfully created restaurant with ID: {}", savedRestaurant.getRestaurantId());
//        return customMapper.toRestaurantDto(savedRestaurant);
//    }

    @Override
    public Page<RestaurantResponseDTO> getAllRestaurants(Pageable pageable) {
        log.info("Fetching restaurants with pagination - Page: {}, Size: {}", pageable.getPageNumber(), pageable.getPageSize());

        return restaurantRepository.findAll(pageable)
                .map(CustomMapper::toRestaurantDto);
    }

    @Override
    public Page<RatingResponseDTO> getRestaurantRatings(Integer restaurantId, Pageable pageable) {
        log.info("Fetching ratings for restaurant ID: {} with pagination - Page: {}, Size: {}", restaurantId, pageable.getPageNumber(), pageable.getPageSize());

        if (!restaurantRepository.existsById(restaurantId)) {
            log.error("Failed to fetch ratings. Restaurant ID {} not found.", restaurantId);
            throw new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId);
        }

        return ratingRepository.findByRestaurant_RestaurantId(restaurantId, pageable)
                .map(CustomMapper::toRatingDto);
    }

}