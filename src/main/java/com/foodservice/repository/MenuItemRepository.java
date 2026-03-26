package com.foodservice.repository;

import com.foodservice.entity.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    // Fetches the menu for a specific restaurant (Supports: GET /api/restaurants/{id}/menu)
    Page<MenuItem> findByRestaurant_RestaurantId(Integer restaurantId, Pageable pageable);

    // Global search for a specific food item across all restaurants
    Page<MenuItem> findByItemNameContainingIgnoreCase(String itemName, Pageable pageable);

    // A custom JPQL query optimized to prevent the N+1 problem
    @Query(value = "SELECT m FROM MenuItem m JOIN FETCH m.restaurant WHERE LOWER(m.itemName) LIKE LOWER(CONCAT('%', :name, '%'))",
            countQuery = "SELECT count(m) FROM MenuItem m WHERE LOWER(m.itemName) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<MenuItem> searchItemsAndFetchRestaurant(@Param("name") String name, Pageable pageable);
}