package com.tastybuddy.tastybuddy_backend.service;


import com.tastybuddy.tastybuddy_backend.dto.FoodRequest;
import com.tastybuddy.tastybuddy_backend.dto.FoodResponse;
import com.tastybuddy.tastybuddy_backend.entity.FoodItem;
import com.tastybuddy.tastybuddy_backend.exception.ResourceNotFoundException;
import com.tastybuddy.tastybuddy_backend.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodResponse addFood(FoodRequest request) {
        FoodItem food = FoodItem.builder()
                .name(request.getName().trim())
                .price(request.getPrice())
                .category(request.getCategory().trim().toUpperCase())
                .imageUrl(request.getImageUrl().trim())
                .build();
        return toResponse(foodRepository.save(food));
    }

    public List<FoodResponse> getAllFoods(String category) {
        List<FoodItem> foods = category == null || category.isBlank()
                ? foodRepository.findAll()
                : foodRepository.findByCategoryIgnoreCase(category.trim());
        return foods.stream().map(this::toResponse).toList();
    }

    public FoodResponse getFoodById(Long id) {
        return foodRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));
    }

    public List<String> getCategories() {
        return foodRepository.findAll()
                .stream()
                .map(FoodItem::getCategory)
                .filter(category -> category != null && !category.isBlank())
                .map(String::trim)
                .distinct()
                .sorted()
                .toList();
    }

    private FoodResponse toResponse(FoodItem food) {
        return new FoodResponse(
                food.getId(),
                food.getName(),
                food.getPrice(),
                food.getCategory(),
                food.getImageUrl()
        );
    }
}
