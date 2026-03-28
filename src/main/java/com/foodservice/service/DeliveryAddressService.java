package com.foodservice.service;

import com.foodservice.entity.dto.DeliveryAddressDTO;
import java.util.List;

public interface DeliveryAddressService {

    List<DeliveryAddressDTO> getAddressesByCustomerId(Integer customerId);

    DeliveryAddressDTO getAddressById(Integer addressId);

    Integer getAddressCount(Integer customerId);

    List<DeliveryAddressDTO> getAddressesByCity(String city);

    DeliveryAddressDTO getDefaultAddress(Integer customerId);
}