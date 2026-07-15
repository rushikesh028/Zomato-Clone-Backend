package com.tastybuddy.tastybuddy_backend.service;



import com.tastybuddy.tastybuddy_backend.dto.CartItemRequest;
import com.tastybuddy.tastybuddy_backend.dto.CartItemResponse;
import com.tastybuddy.tastybuddy_backend.dto.CartItemUpdateRequest;
import com.tastybuddy.tastybuddy_backend.entity.CartItem;
import com.tastybuddy.tastybuddy_backend.entity.FoodItem;
import com.tastybuddy.tastybuddy_backend.exception.ResourceNotFoundException;
import com.tastybuddy.tastybuddy_backend.exception.UnauthorizedException;
import com.tastybuddy.tastybuddy_backend.repository.CartRepository;
import com.tastybuddy.tastybuddy_backend.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final FoodRepository foodRepository;

    public CartItemResponse addToCart(String email, CartItemRequest request) {
        FoodItem foodItem = foodRepository.findById(request.getFoodId())
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

        CartItem item = cartRepository.findByUserEmailAndFoodId(email, request.getFoodId())
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + request.getQuantity());
                    existing.setFoodName(foodItem.getName());
                    return existing;
                })
                .orElse(CartItem.builder()
                        .userEmail(email)
                        .foodId(foodItem.getId())
                        .foodName(foodItem.getName())
                        .quantity(request.getQuantity())
                        .build());

        return toResponse(cartRepository.save(item));
    }

    public List<CartItemResponse> getUserCart(String email) {
        return cartRepository.findByUserEmail(email)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void removeItem(String email, Long id) {
        CartItem item = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        if (!item.getUserEmail().equalsIgnoreCase(email)) {
            throw new UnauthorizedException("You do not have access to this cart item");
        }
        cartRepository.deleteById(id);
    }

    public CartItemResponse updateItem(String email, Long id, CartItemUpdateRequest request) {
        CartItem item = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        if (!item.getUserEmail().equalsIgnoreCase(email)) {
            throw new UnauthorizedException("You do not have access to this cart item");
        }
        item.setQuantity(request.getQuantity());
        return toResponse(cartRepository.save(item));
    }

    public void clearCart(String email) {
        List<CartItem> items = cartRepository.findByUserEmail(email);
        cartRepository.deleteAll(items);
    }

    private CartItemResponse toResponse(CartItem item) {
        return new CartItemResponse(item.getId(), item.getFoodId(), item.getFoodName(), item.getQuantity());
    }
}
