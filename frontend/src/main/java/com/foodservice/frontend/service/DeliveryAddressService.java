package com.foodservice.frontend.service;

import com.foodservice.frontend.entity.dto.DeliveryAddressDTO;

import java.util.List;
import java.util.Map;

public interface DeliveryAddressService {

    DeliveryAddressDTO getAddressById(Integer id, Map<String, String> params, String token);

    List<DeliveryAddressDTO> getAddressesByCity(Map<String , String> params , String token);
}