package com.foodservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopRatedRestaurantDTO {

    private Integer restaurantId;
    private Double averageRating;
}
