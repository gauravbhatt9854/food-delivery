package com.foodservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodservice.entity.Customer;
import com.foodservice.entity.dto.*;
import com.foodservice.exception.OrderInvalidRequestException;
import com.foodservice.service.OrderService;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class, excludeAutoConfiguration = {
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
@DisplayName("Order Controller Tests")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;
    
    @MockBean
    private com.foodservice.security.JwtService jwtService;

    @MockBean
    private com.foodservice.service.impl.UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderCustomerDTO mockOrderCustomerDTO;
    private OrderWithItemDTO mockOrderWithItemDTO;
    private Customer mockCustomer;

    @BeforeEach
    void setUp() {
        mockCustomer = new Customer();
        mockCustomer.setCustomerId(1);
        mockCustomer.setCustomerName("John Doe");
        mockCustomer.setCustomerEmail("john.doe@example.com");

        OrderItemDetailDTO orderItemDetail = new OrderItemDetailDTO();
        orderItemDetail.setOrderDate(LocalDateTime.now());
        orderItemDetail.setOrderStatus("PENDING");
        orderItemDetail.setQuantity(2);
        orderItemDetail.setItemName("Pizza");
        orderItemDetail.setItemDescription("Delicious pizza");
        orderItemDetail.setItemPrice(new BigDecimal("15.99"));
        orderItemDetail.setRestaurantName("Pizza Palace");
        orderItemDetail.setRestaurantAddress("123 Main St");
        orderItemDetail.setRestaurantPhone("555-0123");

        mockOrderCustomerDTO = new OrderCustomerDTO(mockCustomer, List.of(orderItemDetail));

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName("John Doe");
        customerDTO.setCustomerEmail("john.doe@example.com");

        RestaurantResponseDTO restaurantDTO = new RestaurantResponseDTO();
        restaurantDTO.setRestaurantName("Pizza Palace");

        ItemWithQuantity itemWithQuantity = new ItemWithQuantity(
            2, "Pizza", "Delicious pizza", new BigDecimal("15.99")
        );

        mockOrderWithItemDTO = new OrderWithItemDTO();
        mockOrderWithItemDTO.setCustomer(customerDTO);
        mockOrderWithItemDTO.setRestaurant(restaurantDTO);
        mockOrderWithItemDTO.setOrderDate(LocalDateTime.now());
        mockOrderWithItemDTO.setOrderStatus("PENDING");
        mockOrderWithItemDTO.setOrderItems(List.of(itemWithQuantity));
    }

    @Test
    @DisplayName("GET /api/v1/orders/customer/{customerId} - Success")
    void getOrdersByCustomerId_Success() throws Exception {
        when(orderService.getOrdersByCustomerId(1)).thenReturn(mockOrderCustomerDTO);

        mockMvc.perform(get("/api/v1/orders/customer/{customerId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("customer having id: 1 has 1 order"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.customer.customerId").value(1))
                .andExpect(jsonPath("$.data.customer.customerName").value("John Doe"))
                .andExpect(jsonPath("$.data.orderItems").isArray())
                .andExpect(jsonPath("$.data.orderItems[0].itemName").value("Pizza"))
                .andExpect(jsonPath("$.data.orderItems[0].quantity").value(2));
    }

    @Test
    @DisplayName("GET /api/v1/orders/customer/{customerId} - Customer Not Found")
    void getOrdersByCustomerId_CustomerNotFound() throws Exception {
        when(orderService.getOrdersByCustomerId(999))
                .thenThrow(new OrderInvalidRequestException("Customer do next exist having customer id: 999"));

        mockMvc.perform(get("/api/v1/orders/customer/{customerId}", 999))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /api/v1/orders/customer/{customerId} - Empty Order List")
    void getOrdersByCustomerId_EmptyOrderList() throws Exception {
        OrderCustomerDTO emptyOrderDTO = new OrderCustomerDTO(mockCustomer, Collections.emptyList());
        when(orderService.getOrdersByCustomerId(1)).thenReturn(emptyOrderDTO);

        mockMvc.perform(get("/api/v1/orders/customer/{customerId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("customer having id: 1 has 0 order"))
                .andExpect(jsonPath("$.data.customer.customerId").value(1))
                .andExpect(jsonPath("$.data.orderItems").isArray())
                .andExpect(jsonPath("$.data.orderItems").isEmpty());
    }

    @Test
    @DisplayName("GET /api/v1/orders/customer/{customerId} - Invalid Customer ID (Zero)")
    void getOrdersByCustomerId_InvalidCustomerIdZero() throws Exception {
        when(orderService.getOrdersByCustomerId(0))
                .thenThrow(new OrderInvalidRequestException("Customer do next exist having customer id: 0"));

        mockMvc.perform(get("/api/v1/orders/customer/{customerId}", 0))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /api/v1/orders/customer/{customerId} - Negative Customer ID")
    void getOrdersByCustomerId_NegativeCustomerId() throws Exception {
        when(orderService.getOrdersByCustomerId(-1))
                .thenThrow(new OrderInvalidRequestException("Customer do next exist having customer id: -1"));

        mockMvc.perform(get("/api/v1/orders/customer/{customerId}", -1))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /api/v1/orders/detail/{orderId} - Success")
    void getOrderDetailsById_Success() throws Exception {
        when(orderService.getOrderDetailsById(1)).thenReturn(mockOrderWithItemDTO);

        mockMvc.perform(get("/api/v1/orders/detail/{orderId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("order detail having id: 1"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.customer.customerName").value("John Doe"))
                .andExpect(jsonPath("$.data.customer.customerEmail").value("john.doe@example.com"))
                .andExpect(jsonPath("$.data.restaurant.restaurantName").value("Pizza Palace"))
                .andExpect(jsonPath("$.data.orderStatus").value("PENDING"))
                .andExpect(jsonPath("$.data.orderItems").isArray())
                .andExpect(jsonPath("$.data.orderItems[0].itemName").value("Pizza"))
                .andExpect(jsonPath("$.data.orderItems[0].quantity").value(2));
    }

    @Test
    @DisplayName("GET /api/v1/orders/detail/{orderId} - Order Not Found")
    void getOrderDetailsById_OrderNotFound() throws Exception {
        when(orderService.getOrderDetailsById(999))
                .thenThrow(new OrderInvalidRequestException("Order not found having order id: 999"));

        mockMvc.perform(get("/api/v1/orders/detail/{orderId}", 999))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /api/v1/orders/detail/{orderId} - Invalid Order ID (Zero)")
    void getOrderDetailsById_InvalidOrderIdZero() throws Exception {
        when(orderService.getOrderDetailsById(0))
                .thenThrow(new OrderInvalidRequestException("Order not found having order id: 0"));

        mockMvc.perform(get("/api/v1/orders/detail/{orderId}", 0))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /api/v1/orders/detail/{orderId} - Negative Order ID")
    void getOrderDetailsById_NegativeOrderId() throws Exception {
        when(orderService.getOrderDetailsById(-1))
                .thenThrow(new OrderInvalidRequestException("Order not found having order id: -1"));

        mockMvc.perform(get("/api/v1/orders/detail/{orderId}", -1))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /api/v1/orders/detail/{orderId} - Order With Empty Items")
    void getOrderDetailsById_OrderWithEmptyItems() throws Exception {
        OrderWithItemDTO orderWithEmptyItems = new OrderWithItemDTO();
        orderWithEmptyItems.setCustomer(new CustomerDTO());
        orderWithEmptyItems.setRestaurant(new RestaurantResponseDTO());
        orderWithEmptyItems.setOrderDate(LocalDateTime.now());
        orderWithEmptyItems.setOrderStatus("PENDING");
        orderWithEmptyItems.setOrderItems(Collections.emptyList());

        when(orderService.getOrderDetailsById(1)).thenReturn(orderWithEmptyItems);

        mockMvc.perform(get("/api/v1/orders/detail/{orderId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("order detail having id: 1"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.orderItems").isArray())
                .andExpect(jsonPath("$.data.orderItems").isEmpty());
    }

    
    }
