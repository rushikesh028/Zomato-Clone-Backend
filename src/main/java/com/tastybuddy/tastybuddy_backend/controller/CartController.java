package com.tastybuddy.tastybuddy_backend.controller;



import com.tastybuddy.tastybuddy_backend.entity.CartItem;
import com.tastybuddy.tastybuddy_backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Add to cart
    @PostMapping
    public CartItem addToCart(@RequestBody CartItem item) {

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        item.setUserEmail(email);

        return cartService.addToCart(item);
    }

    // View cart
    @GetMapping
    public List<CartItem> getCart() {

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return cartService.getUserCart(email);
    }

    // Remove item
    @DeleteMapping("/{id}")
    public String removeItem(@PathVariable Long id) {
        cartService.removeItem(id);
        return "Item removed";
    }
}
