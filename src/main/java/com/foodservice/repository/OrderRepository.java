package com.foodservice.repository;

import com.foodservice.entity.Order;
import com.foodservice.entity.dto.ItemWithQuantity;
import com.foodservice.entity.dto.OrderItemDetailDTO;
import com.foodservice.entity.dto.RestaurantRevenueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("""
    SELECT
        new com.foodservice.entity.dto.OrderItemDetailDTO(
            o.orderDate,
            o.orderStatus,
            io.quantity,
            mi.itemName,
            mi.itemDescription,
            mi.itemPrice,
            r.restaurantName,
            r.restaurantAddress,
            r.restaurantPhone
        )
    FROM OrderItem io
    JOIN io.order o
    JOIN io.menuItem mi
    JOIN mi.restaurant r
    WHERE o.customer.customerId = :customerId
""")
    List<OrderItemDetailDTO> getOrderDetailsByCustomerId(@Param("customerId") Integer customerId);

    @Query("""
    SELECT
        new com.foodservice.entity.dto.OrderItemDetailDTO(
            o.orderDate,
            o.orderStatus,
            io.quantity,
            mi.itemName,
            mi.itemDescription,
            mi.itemPrice,
            r.restaurantName,
            r.restaurantAddress,
            r.restaurantPhone
        )
    FROM OrderItem io
    JOIN io.order o
    JOIN io.menuItem mi
    JOIN mi.restaurant r
    WHERE o.customer.customerId = :customerId
    AND (:status IS NULL OR o.orderStatus = :status)
""")
    Page<OrderItemDetailDTO> getOrderDetailsByCustomerId(@Param("customerId") Integer customerId, Pageable pageable, @Param("status") String status);

    @Query("""
    SELECT
        new com.foodservice.entity.dto.ItemWithQuantity(
            io.quantity,
            mi.itemName,
            mi.itemDescription,
            mi.itemPrice
        )
    FROM OrderItem io
    JOIN io.menuItem mi
    WHERE io.order.orderId = :orderId
""")
    List<ItemWithQuantity> getOrderItemWithQuantityById(@Param("orderId") Integer orderId);

    List<Order> findByDeliveryDriverDriverId(Integer driverId);

    // Add this query to your existing OrderRepository

    @Query("""
    SELECT new com.foodservice.entity.dto.RestaurantRevenueDTO(
        r.restaurantId,
        r.restaurantName,
        COUNT(DISTINCT o.orderId),
        COALESCE(SUM(oi.quantity * mi.itemPrice), 0),
        COALESCE(SUM(oi.quantity * mi.itemPrice) / NULLIF(COUNT(DISTINCT o.orderId), 0), 0)
    )
    FROM Order o
    JOIN o.restaurant r
    JOIN OrderItem oi ON oi.order.orderId = o.orderId
    JOIN oi.menuItem mi
    WHERE r.restaurantId = :restaurantId
    AND (:fromDate IS NULL OR o.orderDate >= :fromDate)
    AND (:toDate   IS NULL OR o.orderDate <= :toDate)
""")
    RestaurantRevenueDTO getRevenueByRestaurantId(
            @Param("restaurantId") Integer restaurantId,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate);

}