package com.foodservice.frontend.service;

import com.foodservice.frontend.entity.dto.DeliveryAddressDTO;

import java.util.List;

public interface DeliveryAddressService {

    DeliveryAddressDTO getAddressById(Integer id, String token);

    List<DeliveryAddressDTO> getAddressesByCity(String city, String token);
}