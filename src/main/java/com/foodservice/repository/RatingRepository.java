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

import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @Query(value = """
    SELECT r.* FROM ratings r
    JOIN orders o ON r.order_id = o.order_id
    JOIN customers c ON o.customer_id = c.customer_id
    WHERE r.restaurant_id = :restaurantId
    AND (:rating IS NULL OR r.rating = :rating)
    AND (:minRating IS NULL OR r.rating >= :minRating)
    AND (:fromDate IS NULL OR o.order_date >= :fromDate)
    AND (:toDate IS NULL OR o.order_date <= :toDate)
    AND (:keyword IS NULL OR LOWER(r.review) LIKE LOWER(CONCAT('%', :keyword, '%')))
    AND (:customerName IS NULL OR LOWER(c.customer_name) LIKE LOWER(CONCAT('%', :customerName, '%')))
    """, countQuery = """
    SELECT COUNT(*) FROM ratings r
    JOIN orders o ON r.order_id = o.order_id
    JOIN customers c ON o.customer_id = c.customer_id
    WHERE r.restaurant_id = :restaurantId
    AND (:rating IS NULL OR r.rating = :rating)
    AND (:minRating IS NULL OR r.rating >= :minRating)
    AND (:fromDate IS NULL OR o.order_date >= :fromDate)
    AND (:toDate IS NULL OR o.order_date <= :toDate)
    AND (:keyword IS NULL OR LOWER(r.review) LIKE LOWER(CONCAT('%', :keyword, '%')))
    AND (:customerName IS NULL OR LOWER(c.customer_name) LIKE LOWER(CONCAT('%', :customerName, '%')))
    """, nativeQuery = true)
    Page<Rating> findByRestaurantWithFilters(
            @Param("restaurantId") Integer restaurantId,
            @Param("rating") Integer rating,
            @Param("minRating") Integer minRating,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("keyword") String keyword,
            @Param("customerName") String customerName,
            Pageable pageable
    );


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
