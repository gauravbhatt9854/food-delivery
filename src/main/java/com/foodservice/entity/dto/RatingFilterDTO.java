package com.foodservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingFilterDTO {
    private Integer rating;         
    private Integer minRating;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String keyword;
    private String customerName;
}
