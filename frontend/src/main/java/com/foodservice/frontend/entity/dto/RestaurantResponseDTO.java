package com.foodservice.frontend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantResponseDTO {
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhone;
}
