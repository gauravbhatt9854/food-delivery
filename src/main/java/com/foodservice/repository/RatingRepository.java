package com.foodservice.repository;

import com.foodservice.entity.Rating;
import com.foodservice.entity.dto.TopRatedRestaurantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    @Query(value = "SELECT r FROM Rating r JOIN FETCH r.order o JOIN FETCH o.customer WHERE r.restaurant.restaurantId = :restaurantId",
            countQuery = "SELECT count(r) FROM Rating r WHERE r.restaurant.restaurantId = :restaurantId")
    Page<Rating> findByRestaurant_RestaurantId(@Param("restaurantId") Integer restaurantId, Pageable pageable);



    //FOR TOP RATED RESTAURANTS
    @Query("""
    SELECT new com.foodservice.entity.dto.TopRatedRestaurantDTO(
        r.restaurant.restaurantId,
        AVG(r.rating)
    )
    FROM Rating r
    GROUP BY r.restaurant.restaurantId
    ORDER BY AVG(r.rating) DESC
""")
    Page<TopRatedRestaurantDTO> getTopRatedRestaurants(Pageable pageable);
}
