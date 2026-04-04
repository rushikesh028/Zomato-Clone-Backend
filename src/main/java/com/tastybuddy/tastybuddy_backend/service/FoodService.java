package com.tastybuddy.tastybuddy_backend.service;


import com.tastybuddy.tastybuddy_backend.entity.FoodItem;
import com.tastybuddy.tastybuddy_backend.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodItem addFood(FoodItem food) {
        return foodRepository.save(food);
    }

    public List<FoodItem> getAllFoods() {
        return foodRepository.findAll();
    }
}