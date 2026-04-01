package com.foodservice.repository;

import com.foodservice.entity.Order;
import com.foodservice.entity.dto.ItemWithQuantity;
import com.foodservice.entity.dto.OrderItemDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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


}
