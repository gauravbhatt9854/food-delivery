package com.foodservice.repository;

import com.foodservice.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Integer> {


    List<DeliveryAddress> findByCityIgnoreCase(String city);

    List<DeliveryAddress> findByCustomerCustomerId(Integer customerId);

    Integer countByCustomerCustomerId(Integer customerId);

    Optional<DeliveryAddress> findFirstByCustomerCustomerId(Integer customerId);
}