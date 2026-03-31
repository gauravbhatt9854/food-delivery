package com.foodservice.config;

import com.foodservice.entity.dto.*;
import com.foodservice.entity.*;

import java.math.BigDecimal;

import java.util.List;

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
    }

    // Restaurant Mappings
    public static Restaurant toRestaurantEntity(RestaurantRequestDTO dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(dto.getRestaurantName());
        restaurant.setRestaurantAddress(dto.getRestaurantAddress());
        restaurant.setRestaurantPhone(dto.getRestaurantPhone());
        return restaurant;
    }

    public static RestaurantResponseDTO toRestaurantDto(Restaurant entity) {
        RestaurantResponseDTO restaurantResponseDTO = new RestaurantResponseDTO();
        restaurantResponseDTO.setRestaurantName(entity.getRestaurantName());
        restaurantResponseDTO.setRestaurantAddress(entity.getRestaurantAddress());
        restaurantResponseDTO.setRestaurantPhone(entity.getRestaurantPhone());
        return restaurantResponseDTO;
    }

    // MenuItem Mappings
    public static MenuItem toMenuItemEntity(MenuItemRequestDTO dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setItemName(dto.getItemName());
        menuItem.setItemDescription(dto.getItemDescription());
        menuItem.setItemPrice(dto.getItemPrice());
        return menuItem;
    }

    public static MenuItemResponseDTO toMenuItemDto(MenuItem entity) {
        MenuItemResponseDTO menuItemResponseDTO = new MenuItemResponseDTO();
        menuItemResponseDTO.setItemId(entity.getItemId());
        menuItemResponseDTO.setItemName(entity.getItemName());
        menuItemResponseDTO.setItemDescription(entity.getItemDescription());
        menuItemResponseDTO.setItemPrice(entity.getItemPrice());
        if (entity.getRestaurant() != null) {
            menuItemResponseDTO.setRestaurantId(entity.getRestaurant().getRestaurantId());
        }
        return menuItemResponseDTO;
    }

    // order mapper
    public static OrderDTO orderToOrderDTO(Order order, OrderDTO orderDTO) {

        DeliveryDriverDTO deliveryDriverDTO = new DeliveryDriverDTO();
        deliveryDriverDTO.setDriverName(order.getDeliveryDriver().getDriverName());
        deliveryDriverDTO.setDriverPhone(order.getDeliveryDriver().getDriverPhone());
        deliveryDriverDTO.setDriverVehicle(order.getDeliveryDriver().getDriverVehicle());

        orderDTO.setCustomer(customerToCustomerDTO(order.getCustomer()));
        orderDTO.setRestaurant(toRestaurantDto(order.getRestaurant()));
        orderDTO.setDeliveryDriver(deliveryDriverDTO);
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setOrderDate(order.getOrderDate());

        return orderDTO;
    }

    // order with item mapper
    public static OrderWithItemDTO orderToOrderWithItemDTO(Order order, OrderWithItemDTO orderWithItemDTO, List<ItemWithQuantity> itemWithQuantity) {

        orderWithItemDTO.setOrderItems(itemWithQuantity);
        orderWithItemDTO.setCustomer(customerToCustomerDTO(order.getCustomer()));
        orderWithItemDTO.setRestaurant(toRestaurantDto(order.getRestaurant()));
        orderWithItemDTO.setDeliveryDriver(deliveryDriverTODeliveryDriverDTO(order.getDeliveryDriver(), new DeliveryDriverDTO()));
        orderWithItemDTO.setOrderDate(order.getOrderDate());
        orderWithItemDTO.setOrderStatus(order.getOrderStatus());
        return orderWithItemDTO;
    }

    // order item mapper
    public static OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem, OrderItemDTO orderItemDTO) {


        orderItemDTO.setOrderDTO(CustomMapper.orderToOrderDTO(orderItem.getOrder(), new OrderDTO()));
        orderItemDTO.setMenuItemResponseDTO(CustomMapper.toMenuItemDto(orderItem.getMenuItem()));
        orderItemDTO.setQuantity(orderItem.getQuantity());

        return orderItemDTO;
    }



    public static DeliveryAddressDTO deliveryAddressToDTO(DeliveryAddress address) {
        DeliveryAddressDTO dto = new DeliveryAddressDTO();

        dto.setId(address.getAddressId());

        if (address.getCustomer() != null) {
            dto.setCustomerId(address.getCustomer().getCustomerId());
        }

        dto.setAddressLine1(address.getAddressLine1());
        dto.setAddressLine2(address.getAddressLine2());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPostalCode(address.getPostalCode());

        return dto;
    }

    public static DeliveryAddress dtoToDeliveryAddress(DeliveryAddressDTO dto) {
        DeliveryAddress address = new DeliveryAddress();

        address.setAddressId(dto.getId());

        if (dto.getCustomerId() != null) {
            Customer customer = new Customer();
            customer.setCustomerId(dto.getCustomerId());
            address.setCustomer(customer);
        }

        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPostalCode(dto.getPostalCode());

        return address;
    }

    public static RatingResponseDTO toRatingDto(Rating entity) {
        RatingResponseDTO ratingResponseDTO = new RatingResponseDTO();
        ratingResponseDTO.setRating(entity.getRating());
        ratingResponseDTO.setReview(entity.getReview());

        if (entity.getOrder() != null) {
            ratingResponseDTO.setOrderDate(entity.getOrder().getOrderDate());

            if (entity.getOrder().getCustomer() != null) {
                ratingResponseDTO.setCustomerName(entity.getOrder().getCustomer().getCustomerName());
                ratingResponseDTO.setCustomerPhone(entity.getOrder().getCustomer().getCustomerPhone());
            }
        }

        return ratingResponseDTO;
    }

    public static DeliveryDriverDTO deliveryDriverTODeliveryDriverDTO (DeliveryDriver deliveryDriver, DeliveryDriverDTO deliveryDriverDTO) {
        deliveryDriverDTO.setDriverName(deliveryDriver.getDriverName());
        deliveryDriverDTO.setDriverPhone(deliveryDriver.getDriverPhone());
        deliveryDriverDTO.setDriverVehicle(deliveryDriver.getDriverVehicle());
        return deliveryDriverDTO;
    }


}