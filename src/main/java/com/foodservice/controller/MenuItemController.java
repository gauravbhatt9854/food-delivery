package com.foodservice.controller;

import com.foodservice.constants.RestaurantConstants;
import com.foodservice.entity.dto.MenuItemResponseDTO;
import com.foodservice.entity.dto.ApiResponseDTO;
import com.foodservice.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/menu-item")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping("/fetchByRestaurant/{restaurantId}")
    public ResponseEntity<ApiResponseDTO> fetchMenuByRestaurantId(
            @PathVariable Integer restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Fetching menu by restaurant ID: {}", restaurantId);
        Page<MenuItemResponseDTO> menuList = menuItemService.getMenuByRestaurantId(restaurantId, PageRequest.of(page, size));

        log.info("Fetched {} menu items for restaurant ID: {}", menuList.getNumberOfElements(), restaurantId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(RestaurantConstants.STATUS_200, RestaurantConstants.MESSAGE_210, menuList));
    }
}