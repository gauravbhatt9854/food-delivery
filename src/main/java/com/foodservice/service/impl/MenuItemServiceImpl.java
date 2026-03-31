package com.foodservice.service.impl;

import com.foodservice.entity.dto.MenuItemResponseDTO;
import com.foodservice.config.CustomMapper;
import com.foodservice.exception.ResourceNotFoundException;
import com.foodservice.repository.MenuItemRepository;
import com.foodservice.repository.RestaurantRepository;
import com.foodservice.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Page<MenuItemResponseDTO> getMenuByRestaurantId(Integer restaurantId, Integer page, Integer size) {
        log.info("Fetching menu for restaurant ID: {} - Page: {}, Size: {}", restaurantId, page, size);

        if (!restaurantRepository.existsById(restaurantId)) {
            log.error("Failed to fetch menu. Restaurant ID {} not found.", restaurantId);
            throw new ResourceNotFoundException("Restaurant not found with ID: " + restaurantId);
        }

        return menuItemRepository.findByRestaurant_RestaurantId(restaurantId, PageRequest.of(page, size))
                .map(CustomMapper::toMenuItemDto);
    }
}