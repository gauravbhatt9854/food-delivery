package com.foodservice.repository;

import com.foodservice.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer , Integer> {

    @Query("""
    SELECT DISTINCT a.customer
    FROM DeliveryAddress a
    WHERE LOWER(a.city) LIKE LOWER(CONCAT('%', :city, '%'))
""")
    Page<Customer> findCustomersByCity(@Param("city") String city, Pageable pageable);
}
