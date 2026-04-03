package com.foodservice.controller;

import com.foodservice.entity.dto.DeliveryDriverResponseDTO;
import com.foodservice.service.DeliveryDriverService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.TestPropertySource;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
	    controllers = DeliveryDriverController.class,
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
	@AutoConfigureMockMvc(addFilters = false)
	class DeliveryDriverControllerTest {

	    @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private DeliveryDriverService deliveryDriverService;

	    @MockBean
	    private com.foodservice.security.JwtService jwtService;

	    @MockBean
	    private com.foodservice.service.impl.UserDetailsServiceImpl userDetailsService;

	    @Autowired
	    private ObjectMapper objectMapper;

    // ---------------- GET DRIVER BY ID ----------------
    @Test
    void testGetDriverById() throws Exception {

        DeliveryDriverResponseDTO dto = new DeliveryDriverResponseDTO();
        dto.setDriverId(1);
        dto.setDriverName("Samar");

        Mockito.when(deliveryDriverService.getDriverById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/drivers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.driverId").value(1))
                .andExpect(jsonPath("$.data.driverName").value("Samar"));
    }

    // ---------------- GET ALL DRIVERS ----------------
    @Test
    void testGetAllDrivers() throws Exception {

        DeliveryDriverResponseDTO dto = new DeliveryDriverResponseDTO();
        dto.setDriverId(1);

        Mockito.when(deliveryDriverService.getAllDrivers())
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/drivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].driverId").value(1));
    }

    // ---------------- GET DRIVER DELIVERIES ----------------
    @Test
    void testGetDriverDeliveries() throws Exception {

        DeliveryDriverResponseDTO dto = new DeliveryDriverResponseDTO();
        dto.setDriverId(1);

        Mockito.when(deliveryDriverService.getDriverDeliveries(1))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/drivers/1/deliveries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].driverId").value(1));
    }

    // ---------------- GET ALL DRIVERS SORTED ----------------
    @Test
    void testGetAllDriversSorted() throws Exception {

        DeliveryDriverResponseDTO dto = new DeliveryDriverResponseDTO();
        dto.setDriverId(1);

        Mockito.when(deliveryDriverService.getAllDrivers("asc"))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/drivers/sorted?sort=asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].driverId").value(1));
    }

    // ---------------- GET ORDERS BY DRIVER ----------------
    @Test
    void testGetOrdersByDriver() throws Exception {

        DeliveryDriverResponseDTO dto = new DeliveryDriverResponseDTO();
        dto.setOrderId(100);

        Mockito.when(deliveryDriverService.getOrderByDriver(1))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/drivers/1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].orderId").value(100));
    }

    // ---------------- GET CUSTOMERS BY DRIVER ----------------
    @Test
    void testGetCustomersByDriver() throws Exception {

        DeliveryDriverResponseDTO dto = new DeliveryDriverResponseDTO();
        dto.setCustomerName("Rahul");

        Mockito.when(deliveryDriverService.getcustomerByDriver(1))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/drivers/1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].customerName").value("Rahul"));
    }

    // ---------------- GET CUSTOMER ORDERS BY DRIVER ----------------
    @Test
    void testGetCustomerOrdersByDriver() throws Exception {

        DeliveryDriverResponseDTO dto = new DeliveryDriverResponseDTO();
        dto.setOrderId(200);

        Mockito.when(deliveryDriverService.getCustomerOrderByDriver(1, 10))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/drivers/1/customers/10/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].orderId").value(200));
    }

    // ---------------- GET RESTAURANTS BY DRIVER ----------------
    @Test
    void testGetRestaurantsByDriver() throws Exception {

        DeliveryDriverResponseDTO dto = new DeliveryDriverResponseDTO();
        dto.setResturentName("Dominos");

        Mockito.when(deliveryDriverService.getRestaurantsByDriver(1))
                .thenReturn(List.of(dto));

        // ✅ FIXED URL
        mockMvc.perform(get("/api/v1/drivers/1/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].resturentName").value("Dominos"));
    }

    // ---------------- GET TOTAL ORDERS ----------------
    @Test
    void testGetTotalOrdersByDriver() throws Exception {

        DeliveryDriverResponseDTO dto = new DeliveryDriverResponseDTO();

        Mockito.when(deliveryDriverService.getOrderByDriver(1))
                .thenReturn(List.of(dto, dto));

        // FIXED URL
        mockMvc.perform(get("/api/v1/drivers/1/total-orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(2));
    }
}