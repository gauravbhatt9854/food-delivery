package com.foodservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodservice.entity.dto.*;
import com.foodservice.exception.ResourceNotFoundException;
import com.foodservice.service.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderItemController.class, excludeAutoConfiguration = {
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration.class
})
@TestPropertySource(properties = {
    "jwt.secret=testSecretKey12345678901234567890123456789012345678901234567890",
    "jwt.expiration=86400000"
})
@DisplayName("Order Item Controller Tests")
class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderItemService orderItemService;

    @MockBean
    private com.foodservice.security.JwtService jwtService;

    @MockBean
    private com.foodservice.service.impl.UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderItemDTO mockOrderItemDTO;

    @BeforeEach
    void setUp() {
        OrderDTO mockOrderDTO = new OrderDTO();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName("John Doe");
        customerDTO.setCustomerEmail("john.doe@example.com");
        RestaurantResponseDTO restaurantDTO = new RestaurantResponseDTO();
        restaurantDTO.setRestaurantName("Pizza Palace");
        
        mockOrderDTO.setCustomer(customerDTO);
        mockOrderDTO.setRestaurant(restaurantDTO);
        mockOrderDTO.setOrderDate(LocalDateTime.now());
        mockOrderDTO.setOrderStatus("PENDING");

        MenuItemResponseDTO mockMenuItemDTO = new MenuItemResponseDTO();
        mockMenuItemDTO.setItemId(1);
        mockMenuItemDTO.setItemName("Pizza");
        mockMenuItemDTO.setItemDescription("Delicious pizza with cheese");
        mockMenuItemDTO.setItemPrice(new BigDecimal("15.99"));
        mockMenuItemDTO.setRestaurantId(1);

        mockOrderItemDTO = new OrderItemDTO();
        mockOrderItemDTO.setOrderDTO(mockOrderDTO);
        mockOrderItemDTO.setMenuItemResponseDTO(mockMenuItemDTO);
        mockOrderItemDTO.setQuantity(2);
    }

    @Test
    @DisplayName("GET /api/v1/order-items/{orderItemId} - Success")
    void getOrderItemDetailsById_Success() throws Exception {
        when(orderItemService.getOrderItemById(1)).thenReturn(mockOrderItemDTO);

        mockMvc.perform(get("/api/v1/order-items/{orderItemId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("order item detail having id: 1"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.orderDTO.orderStatus").value("PENDING"))
                .andExpect(jsonPath("$.data.orderDTO.customer.customerName").value("John Doe"))
                .andExpect(jsonPath("$.data.orderDTO.restaurant.restaurantName").value("Pizza Palace"))
                .andExpect(jsonPath("$.data.menuItemResponseDTO.itemId").value(1))
                .andExpect(jsonPath("$.data.menuItemResponseDTO.itemName").value("Pizza"))
                .andExpect(jsonPath("$.data.menuItemResponseDTO.itemDescription").value("Delicious pizza with cheese"))
                .andExpect(jsonPath("$.data.menuItemResponseDTO.itemPrice").value(15.99))
                .andExpect(jsonPath("$.data.menuItemResponseDTO.restaurantId").value(1))
                .andExpect(jsonPath("$.data.quantity").value(2));
    }

    @Test
    @DisplayName("GET /api/v1/order-items/{orderItemId} - Order Item Not Found")
    void getOrderItemDetailsById_OrderItemNotFound() throws Exception {
        when(orderItemService.getOrderItemById(999))
                .thenThrow(new ResourceNotFoundException("Order item not found with id: 999"));

        mockMvc.perform(get("/api/v1/order-items/{orderItemId}", 999))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /api/v1/order-items/{orderItemId} - Invalid Order Item ID (Zero)")
    void getOrderItemDetailsById_InvalidOrderItemIdZero() throws Exception {
        when(orderItemService.getOrderItemById(0))
                .thenThrow(new ResourceNotFoundException("Order item not found with id: 0"));

        mockMvc.perform(get("/api/v1/order-items/{orderItemId}", 0))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /api/v1/order-items/{orderItemId} - Negative Order Item ID")
    void getOrderItemDetailsById_NegativeOrderItemId() throws Exception {
        when(orderItemService.getOrderItemById(-1))
                .thenThrow(new ResourceNotFoundException("Order item not found with id: -1"));

        mockMvc.perform(get("/api/v1/order-items/{orderItemId}", -1))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /api/v1/order-items/{orderItemId} - Order Item With Empty Description")
    void getOrderItemDetailsById_OrderItemWithEmptyDescription() throws Exception {
        OrderItemDTO orderItemWithEmptyDesc = new OrderItemDTO();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDate(LocalDateTime.now());
        orderDTO.setOrderStatus("PENDING");

        MenuItemResponseDTO emptyDescMenuItemDTO = new MenuItemResponseDTO();
        emptyDescMenuItemDTO.setItemId(1);
        emptyDescMenuItemDTO.setItemName("Simple Pizza");
        emptyDescMenuItemDTO.setItemDescription("");
        emptyDescMenuItemDTO.setItemPrice(new BigDecimal("10.99"));
        emptyDescMenuItemDTO.setRestaurantId(1);

        orderItemWithEmptyDesc.setOrderDTO(orderDTO);
        orderItemWithEmptyDesc.setMenuItemResponseDTO(emptyDescMenuItemDTO);
        orderItemWithEmptyDesc.setQuantity(1);

        when(orderItemService.getOrderItemById(1)).thenReturn(orderItemWithEmptyDesc);

        mockMvc.perform(get("/api/v1/order-items/{orderItemId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("order item detail having id: 1"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.menuItemResponseDTO.itemName").value("Simple Pizza"))
                .andExpect(jsonPath("$.data.menuItemResponseDTO.itemDescription").value(""))
                .andExpect(jsonPath("$.data.quantity").value(1));
    }
}
