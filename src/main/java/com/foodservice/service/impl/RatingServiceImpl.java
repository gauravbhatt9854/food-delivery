package com.foodservice.service.impl;

import com.foodservice.entity.dto.TopRatedRestaurantDTO;
import com.foodservice.exception.ResourceNotFoundException;
import com.foodservice.repository.RatingRepository;
import com.foodservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Override
    public Page<TopRatedRestaurantDTO> getTopRatedRestaurants(int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "averageRating")
        );

        Page<TopRatedRestaurantDTO> result =
                ratingRepository.getTopRatedRestaurants(pageable);

        if (result.isEmpty()) {
            throw new ResourceNotFoundException("No ratings available");
        }

        return result;
    }
}