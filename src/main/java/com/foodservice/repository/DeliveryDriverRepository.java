package com.foodservice.repository;

import com.foodservice.entity.DeliveryDriver;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryDriverRepository extends JpaRepository<DeliveryDriver, Integer> {
	List<DeliveryDriver> findAllByOrderByDriverNameAsc();
	List<DeliveryDriver> findAllByOrderByDriverNameDesc();

}