package com.tastybuddy.tastybuddy_backend.service;



import com.tastybuddy.tastybuddy_backend.entity.CartItem;
import com.tastybuddy.tastybuddy_backend.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public CartItem addToCart(CartItem item) {
        return cartRepository.save(item);
    }

    public List<CartItem> getUserCart(String email) {
        return cartRepository.findByUserEmail(email);
    }

    public void removeItem(Long id) {
        cartRepository.deleteById(id);
    }
}