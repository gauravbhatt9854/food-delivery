package com.foodservice.config;

import com.foodservice.entity.Customer;
import com.foodservice.entity.MenuItem;
import com.foodservice.entity.Restaurant;
import com.foodservice.entity.dto.*;

public class CustomMapper {

    public static CustomerDTO customerToCustomerDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerName(customer.getCustomerName());
        dto.setCustomerEmail(customer.getCustomerEmail());
        dto.setCustomerPhone(customer.getCustomerPhone());
        return dto;
    }

    public static Customer customerDTOToCustomer(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setCustomerName(dto.getCustomerName());
        customer.setCustomerEmail(dto.getCustomerEmail());
        customer.setCustomerPhone(dto.getCustomerPhone());
        return customer;
        public static Customer customerDTOToCustomer(CustomerDTO dto) {
            Customer customer = new Customer();
            customer.setId(dto.getId());
            customer.setName(dto.getName());
            customer.setEmail(dto.getEmail());
            customer.setPhone(dto.getPhone());
            return customer;
        }

    // Restaurant Mappings
    public Restaurant toRestaurantEntity(RestaurantRequestDTO dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(dto.getRestaurantName());
        restaurant.setRestaurantAddress(dto.getRestaurantAddress());
        restaurant.setRestaurantPhone(dto.getRestaurantPhone());
        return restaurant;
    }

    public RestaurantResponseDTO toRestaurantDto(Restaurant entity) {
        RestaurantResponseDTO dto = new RestaurantResponseDTO();
        dto.setRestaurantId(entity.getRestaurantId());
        dto.setRestaurantName(entity.getRestaurantName());
        dto.setRestaurantAddress(entity.getRestaurantAddress());
        dto.setRestaurantPhone(entity.getRestaurantPhone());
        return dto;
    }

    // MenuItem Mappings
    public MenuItem toMenuItemEntity(MenuItemRequestDTO dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setItemName(dto.getItemName());
        menuItem.setItemDescription(dto.getItemDescription());
        menuItem.setItemPrice(dto.getItemPrice());
        return menuItem;
    }

    public MenuItemResponseDTO toMenuItemDto(MenuItem entity) {
        MenuItemResponseDTO dto = new MenuItemResponseDTO();
        dto.setItemId(entity.getItemId());
        dto.setItemName(entity.getItemName());
        dto.setItemDescription(entity.getItemDescription());
        dto.setItemPrice(entity.getItemPrice());
        if (entity.getRestaurant() != null) {
            dto.setRestaurantId(entity.getRestaurant().getRestaurantId());
        }
        return dto;
    }
}
