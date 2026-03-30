package com.foodservice.service;

import com.foodservice.entity.dto.MenuItemRequestDTO;
import com.foodservice.entity.dto.MenuItemResponseDTO;
import org.springframework.data.domain.Page;

public interface MenuItemService {

    Page<MenuItemResponseDTO> getMenuByRestaurantId(Integer restaurantId, Integer page, Integer size);
}