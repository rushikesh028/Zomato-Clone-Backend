package com.tastybuddy.tastybuddy_backend.controller;

import com.tastybuddy.tastybuddy_backend.entity.FoodItem;
import com.tastybuddy.tastybuddy_backend.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    //  Add Food
    @PostMapping
    public FoodItem addFood(@RequestBody FoodItem food) {
        return foodService.addFood(food);
    }

    //  Get All Foods
    @GetMapping
    public List<FoodItem> getAllFoods() {
        return foodService.getAllFoods();
    }
}