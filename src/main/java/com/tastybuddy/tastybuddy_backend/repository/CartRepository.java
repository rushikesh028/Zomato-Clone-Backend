package com.tastybuddy.tastybuddy_backend.repository;

import com.tastybuddy.tastybuddy_backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUserEmail(String userEmail);
}