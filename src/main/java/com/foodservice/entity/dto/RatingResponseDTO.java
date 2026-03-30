package com.foodservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class RatingResponseDTO {

    private Integer rating;
    private String review;

    private String customerName;

    private String restaurantName;
    private LocalDateTime orderDate;
}