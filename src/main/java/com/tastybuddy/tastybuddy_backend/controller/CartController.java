package com.tastybuddy.tastybuddy_backend.controller;

import com.tastybuddy.tastybuddy_backend.dto.ApiMessageResponse;
import com.tastybuddy.tastybuddy_backend.dto.CartItemRequest;
import com.tastybuddy.tastybuddy_backend.dto.CartItemResponse;
import com.tastybuddy.tastybuddy_backend.dto.CartItemUpdateRequest;
import com.tastybuddy.tastybuddy_backend.service.CartService;
import com.tastybuddy.tastybuddy_backend.service.CurrentUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Manage the authenticated user's cart")
@SecurityRequirement(name = "bearerAuth")
public class CartController {

    private final CartService cartService;
    private final CurrentUserService currentUserService;

    @PostMapping
    @Operation(summary = "Add item to cart")
    public ResponseEntity<CartItemResponse> addToCart(@Valid @RequestBody CartItemRequest request) {
        String email = currentUserService.getCurrentUserEmail();
        return ResponseEntity.ok(cartService.addToCart(email, request));
    }

    @GetMapping
    @Operation(summary = "Get current user's cart")
    public ResponseEntity<List<CartItemResponse>> getCart() {
        String email = currentUserService.getCurrentUserEmail();
        return ResponseEntity.ok(cartService.getUserCart(email));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update cart item quantity")
    public ResponseEntity<CartItemResponse> updateItem(@PathVariable Long id,
                                                       @Valid @RequestBody CartItemUpdateRequest request) {
        return ResponseEntity.ok(cartService.updateItem(currentUserService.getCurrentUserEmail(), id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove item from cart")
    public ResponseEntity<ApiMessageResponse> removeItem(@PathVariable Long id) {
        cartService.removeItem(currentUserService.getCurrentUserEmail(), id);
        return ResponseEntity.ok(new ApiMessageResponse("Item removed successfully"));
    }

    @DeleteMapping
    @Operation(summary = "Clear current user's cart")
    public ResponseEntity<ApiMessageResponse> clearCart() {
        cartService.clearCart(currentUserService.getCurrentUserEmail());
        return ResponseEntity.ok(new ApiMessageResponse("Cart cleared successfully"));
    }
}
