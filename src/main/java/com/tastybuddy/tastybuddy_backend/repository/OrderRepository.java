package com.tastybuddy.tastybuddy_backend.repository;

import com.tastybuddy.tastybuddy_backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double getTotalRevenue();

    List<Order> findByUserEmail(String email);
}