package com.foodservice.service;

import com.foodservice.entity.dto.TopRatedRestaurantDTO;
import org.springframework.data.domain.Page;

public interface RatingService {

    Page<TopRatedRestaurantDTO> getTopRatedRestaurants(int page, int size);
}
