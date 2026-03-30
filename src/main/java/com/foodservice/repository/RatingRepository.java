package com.foodservice.repository;

import com.foodservice.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    @Query(value = "SELECT r FROM Rating r JOIN FETCH r.order o JOIN FETCH o.customer WHERE r.restaurant.restaurantId = :restaurantId",
            countQuery = "SELECT count(r) FROM Rating r WHERE r.restaurant.restaurantId = :restaurantId")
    Page<Rating> findByRestaurant_RestaurantId(@Param("restaurantId") Integer restaurantId, Pageable pageable);
}
