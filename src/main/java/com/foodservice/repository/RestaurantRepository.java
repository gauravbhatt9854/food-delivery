package com.foodservice.repository;

import com.foodservice.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    // Allows searching for restaurants by name with pagination
    Page<Restaurant> findByRestaurantNameContainingIgnoreCases(String name, Pageable pageable);

    // Highly efficient in preventing duplicate phone numbers
    boolean existsByRestaurantPhone(String phone);

}
