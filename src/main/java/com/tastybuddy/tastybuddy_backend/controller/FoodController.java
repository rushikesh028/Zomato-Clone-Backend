package com.tastybuddy.tastybuddy_backend.controller;

import com.tastybuddy.tastybuddy_backend.dto.FoodRequest;
import com.tastybuddy.tastybuddy_backend.dto.FoodResponse;
import com.tastybuddy.tastybuddy_backend.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
@Tag(name = "Foods", description = "Food catalog operations")
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    @Operation(summary = "Create food item", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<FoodResponse> addFood(@Valid @RequestBody FoodRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodService.addFood(request));
    }

    @GetMapping
    @Operation(summary = "List foods", description = "Public food catalog endpoint with optional category filter")
    public ResponseEntity<List<FoodResponse>> getAllFoods(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(foodService.getAllFoods(category));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get food by id")
    public ResponseEntity<FoodResponse> getFoodById(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.getFoodById(id));
    }

    @GetMapping("/categories")
    @Operation(summary = "List food categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(foodService.getCategories());
    }
}
