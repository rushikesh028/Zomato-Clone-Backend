package com.tastybuddy.tastybuddy_backend.repository;


import com.tastybuddy.tastybuddy_backend.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<FoodItem, Long> {

    List<FoodItem> findByCategoryIgnoreCase(String category);
}
