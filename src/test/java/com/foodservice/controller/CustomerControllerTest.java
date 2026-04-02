package com.foodservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodservice.entity.dto.*;
import com.foodservice.exception.ResourceNotFoundException;
import com.foodservice.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = CustomerController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration.class
        }
)
@TestPropertySource(properties = {
        "jwt.secret=testSecretKey12345678901234567890123456789012345678901234567890",
        "jwt.expiration=86400000"
})
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private com.foodservice.security.JwtService jwtService;

    @MockBean
    private com.foodservice.service.impl.UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDTO customerDTO;
    private DeliveryAddressDTO addressDTO;
    private CustomerAnalyticsDTO analytics;
    private OrderItemDetailDTO order;

    @BeforeEach
    void setUp() {
        customerDTO = new CustomerDTO();
        customerDTO.setCustomerName("John Doe");
        customerDTO.setCustomerEmail("john@example.com");
        customerDTO.setCustomerPhone("9999999999");

        addressDTO = new DeliveryAddressDTO();
        addressDTO.setId(10);
        addressDTO.setCustomerId(1);
        addressDTO.setAddressLine1("Haldwani Street");
        addressDTO.setAddressLine2("Near Market");
        addressDTO.setCity("Haldwani");
        addressDTO.setState("Uttarakhand");
        addressDTO.setPostalCode("263139");


        order = new OrderItemDetailDTO(
                LocalDateTime.now(),
                "DELIVERED",
                2,
                "Pizza",
                "Cheese Pizza",
                new BigDecimal("299.99"),
                "Dominos",
                "Delhi",
                "8888888888"
        );

        analytics = new CustomerAnalyticsDTO();
        analytics.setTotalOrders(1);
        analytics.setTotalSpend(35.97);
        analytics.setAvgOrderValue(35.97);
    }

    // ================== GET CUSTOMER BY ID ==================

    @Test
    @DisplayName("GET /customers/{id} - Success")
    void getCustomerById_Success() throws Exception {
        when(customerService.getCustomerById(1)).thenReturn(customerDTO);

        mockMvc.perform(get("/api/v1/customers/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.customerName").value("John Doe"))
                .andExpect(jsonPath("$.data.customerEmail").value("john@example.com"))
                .andExpect(jsonPath("$.data.customerPhone").value("9999999999"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // ================== GET ADDRESSES ==================

    @Test
    @DisplayName("GET /customers/{id}/addresses - Success")
    void getAddressesByCustomerId_Success() throws Exception {
        when(customerService.getAddressesByCustomerId(1))
                .thenReturn(List.of(addressDTO));

        mockMvc.perform(get("/api/v1/customers/{id}/addresses", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(10))
                .andExpect(jsonPath("$.data[0].customerId").value(1))
                .andExpect(jsonPath("$.data[0].addressLine1").value("Haldwani Street"))
                .andExpect(jsonPath("$.data[0].addressLine2").value("Near Market"))
                .andExpect(jsonPath("$.data[0].city").value("Haldwani"))
                .andExpect(jsonPath("$.data[0].state").value("Uttarakhand"))
                .andExpect(jsonPath("$.data[0].postalCode").value("263139"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // ================== GET ORDERS ==================

    @Test
    @DisplayName("GET /customers/{id}/orders - Success")
    void getOrdersByCustomerId_Success() throws Exception {
        when(customerService.getOrdersByCustomerId(1))
                .thenReturn(List.of(order));

        mockMvc.perform(get("/api/v1/customers/{id}/orders", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // response structure
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").exists())

                // data validations
                .andExpect(jsonPath("$.data").isArray())

                .andExpect(jsonPath("$.data[0].itemName").value("Pizza"))
                .andExpect(jsonPath("$.data[0].itemDescription").value("Cheese Pizza"))
                .andExpect(jsonPath("$.data[0].orderStatus").value("DELIVERED"))
                .andExpect(jsonPath("$.data[0].quantity").value(2))
                .andExpect(jsonPath("$.data[0].itemPrice").value(299.99))
                .andExpect(jsonPath("$.data[0].restaurantName").value("Dominos"))
                .andExpect(jsonPath("$.data[0].restaurantAddress").value("Delhi"))
                .andExpect(jsonPath("$.data[0].restaurantPhone").value("8888888888"))

                // optional (date check)
                .andExpect(jsonPath("$.data[0].orderDate").exists())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    // ================== GET ALL CUSTOMERS ==================

    @Test
    @DisplayName("GET /customers?page&size - Success")
    void getAllCustomers_Success() throws Exception {
        when(customerService.getAllCustomers(0, 5))
                .thenReturn(List.of(customerDTO));

        mockMvc.perform(get("/api/v1/customers")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].customerName").value("John Doe"))
                .andExpect(jsonPath("$.data[0].customerEmail").value("john@example.com"))
                .andExpect(jsonPath("$.data[0].customerPhone").value("9999999999"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // ================== GET BY CITY ==================

    @Test
    @DisplayName("GET /customers/city - Success")
    void getCustomersByCity_Success() throws Exception {
        when(customerService.getCustomersByCity("Delhi"))
                .thenReturn(List.of(customerDTO));

        mockMvc.perform(get("/api/v1/customers/city")
                        .param("city", "Delhi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data[0].customerName").value("John Doe"))
                .andExpect(jsonPath("$.data[0].customerEmail").value("john@example.com"))
                .andExpect(jsonPath("$.data[0].customerPhone").value("9999999999"));
    }

    // ================== ADDRESS COUNT ==================

    @Test
    @DisplayName("GET /customers/{id}/address-count - Success")
    void getAddressCount_Success() throws Exception {
        when(customerService.getAddressCount(1)).thenReturn(3);

        mockMvc.perform(get("/api/v1/customers/{id}/address-count", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(3))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").exists());
    }

    // ================== ANALYTICS ==================

    @Test
    @DisplayName("GET /customers/{id}/analytics - Success")
    void getCustomerAnalytics_Success() throws Exception {

        when(customerService.getCustomerAnalytics(1)).thenReturn(analytics);

        mockMvc.perform(get("/api/v1/customers/{id}/analytics", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Status & message
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Customer analytics data retrieved successfully"))

                // Data validation (VERY IMPORTANT)
                .andExpect(jsonPath("$.data.totalOrders").value(1))
                .andExpect(jsonPath("$.data.totalSpend").value(35.97))
                .andExpect(jsonPath("$.data.avgOrderValue").value(35.97));
    }

    @Test
    @DisplayName("GET /customers/{id} - Not Found")
    void getCustomerById_NotFound() throws Exception {

        when(customerService.getCustomerById(999))
                .thenThrow(new ResourceNotFoundException("Customer not found"));

        mockMvc.perform(get("/api/v1/customers/{id}", 999))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /customers/{id} - Invalid ID (0)")
    void getCustomerById_InvalidIdZero() throws Exception {

        when(customerService.getCustomerById(0))
                .thenThrow(new ResourceNotFoundException("Invalid ID"));

        mockMvc.perform(get("/api/v1/customers/{id}", 0))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /customers/{id} - Negative ID")
    void getCustomerById_NegativeId() throws Exception {

        when(customerService.getCustomerById(-1))
                .thenThrow(new ResourceNotFoundException("Invalid ID"));

        mockMvc.perform(get("/api/v1/customers/{id}", -1))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /customers/{id}/addresses - Not Found (No Addresses)")
    void getAddresses_NotFound_ForEmpty() throws Exception {

        when(customerService.getAddressesByCustomerId(1))
                .thenThrow(new ResourceNotFoundException("No addresses found"));

        mockMvc.perform(get("/api/v1/customers/{id}/addresses", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("GET /customers/{id}/orders - Empty List")
    void getOrders_EmptyList() throws Exception {

        when(customerService.getOrdersByCustomerId(1))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/customers/{id}/orders", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    @DisplayName("GET /customers/{id}/orders - Not Found")
    void getOrders_NotFound() throws Exception {

        when(customerService.getOrdersByCustomerId(999))
                .thenThrow(new ResourceNotFoundException("Customer not found"));

        mockMvc.perform(get("/api/v1/customers/{id}/orders", 999))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /customers/city - Not Found (No Customers in City)")
    void getCustomersByCity_NotFound() throws Exception {

        when(customerService.getCustomersByCity("UnknownCity"))
                .thenThrow(new ResourceNotFoundException("No customers found in city"));

        mockMvc.perform(get("/api/v1/customers/city")
                        .param("city", "UnknownCity"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("GET /customers/{id}/analytics - Not Found")
    void getAnalytics_NotFound() throws Exception {

        when(customerService.getCustomerAnalytics(999))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/v1/customers/{id}/analytics", 999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("GET /customers/{id}/address-count - Zero Count")
    void getAddressCount_Zero() throws Exception {

        when(customerService.getAddressCount(1)).thenReturn(0);

        mockMvc.perform(get("/api/v1/customers/{id}/address-count", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(0))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("GET /customers?page&size - Not Found")
    void getAllCustomers_NotFound() throws Exception {

        when(customerService.getAllCustomers(0, 5))
                .thenThrow(new ResourceNotFoundException("No customers found"));

        mockMvc.perform(get("/api/v1/customers")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists());

    }


    @Test
    @DisplayName("GET /customers/{id}/analytics - Not Found (Empty Data)")
    void getAnalytics_EmptyData() throws Exception {

        when(customerService.getCustomerAnalytics(1))
                .thenThrow(new ResourceNotFoundException("No analytics data"));

        mockMvc.perform(get("/api/v1/customers/{id}/analytics", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists());
    }


    @Test
    @DisplayName("GET /customers/city - Missing city param")
    void getCustomersByCity_MissingParam() throws Exception {

        mockMvc.perform(get("/api/v1/customers/city"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /customers - Invalid page/size")
    void getAllCustomers_InvalidPage() throws Exception {

        mockMvc.perform(get("/api/v1/customers")
                        .param("page", "-1")
                        .param("size", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}