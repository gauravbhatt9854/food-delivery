package com.foodservice.frontend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Page {
    private Integer size;
    private Integer number;
    private Integer totalElements;
    private Integer totalPages;
}
