package com.tastybuddy.tastybuddy_backend.repository;

import com.tastybuddy.tastybuddy_backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUserEmail(String userEmail);

    Optional<CartItem> findByUserEmailAndFoodId(String userEmail, Long foodId);
}
