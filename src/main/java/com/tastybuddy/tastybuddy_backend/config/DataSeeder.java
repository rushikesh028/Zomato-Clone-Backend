package com.tastybuddy.tastybuddy_backend.config;

import com.tastybuddy.tastybuddy_backend.entity.FoodItem;
import com.tastybuddy.tastybuddy_backend.entity.User;
import com.tastybuddy.tastybuddy_backend.repository.FoodRepository;
import com.tastybuddy.tastybuddy_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${app.seed-data:true}")
    private boolean seedData;

    @Value("${app.seed.admin-email:admin@tastybuddy.local}")
    private String adminEmail;

    @Value("${app.seed.admin-password:Admin@123}")
    private String adminPassword;

    @Bean
    ApplicationRunner seedData() {
        return args -> {
            if (!seedData) {
                return;
            }

            seedAdminUser();
            seedFoods();
        };
    }

    private void seedAdminUser() {
        String normalizedEmail = adminEmail.trim().toLowerCase();
        if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            return;
        }

        userRepository.save(User.builder()
                .name("TastyBuddy Admin")
                .email(normalizedEmail)
                .password(passwordEncoder.encode(adminPassword))
                .role("ADMIN")
                .build());
    }

    private void seedFoods() {
        if (foodRepository.count() > 0) {
            return;
        }

        foodRepository.saveAll(List.of(
                FoodItem.builder().name("Margherita Pizza").price(349).category("VEG").imageUrl("https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=600&q=80").build(),
                FoodItem.builder().name("Farmhouse Pizza").price(429).category("VEG").imageUrl("https://images.unsplash.com/photo-1513104890138-7c749659a591?w=600&q=80").build(),
                FoodItem.builder().name("Chicken Biryani").price(359).category("NON_VEG").imageUrl("https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=600&q=80").build(),
                FoodItem.builder().name("Butter Chicken").price(379).category("NON_VEG").imageUrl("https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?w=600&q=80").build(),
                FoodItem.builder().name("Paneer Tikka Wrap").price(229).category("VEG").imageUrl("https://images.unsplash.com/photo-1511690656952-34342bb7c2f2?w=600&q=80").build(),
                FoodItem.builder().name("Veg Hakka Noodles").price(249).category("VEG").imageUrl("https://images.unsplash.com/photo-1612929633738-8fe44f7ec841?w=600&q=80").build(),
                FoodItem.builder().name("Schezwan Fried Rice").price(269).category("VEG").imageUrl("https://images.unsplash.com/photo-1512058564366-18510be2db19?w=600&q=80").build(),
                FoodItem.builder().name("Grilled Chicken Burger").price(289).category("NON_VEG").imageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=600&q=80").build(),
                FoodItem.builder().name("Classic Fries").price(129).category("SNACK").imageUrl("https://images.unsplash.com/photo-1576107232684-1279f390859f?w=600&q=80").build(),
                FoodItem.builder().name("Cold Coffee").price(149).category("BEVERAGE").imageUrl("https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=600&q=80").build(),
                FoodItem.builder().name("Chocolate Shake").price(169).category("BEVERAGE").imageUrl("https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=600&q=80").build(),
                FoodItem.builder().name("Red Velvet Pastry").price(189).category("DESSERT").imageUrl("https://images.unsplash.com/photo-1563729784474-d77dbb933a9e?w=600&q=80").build()
        ));
    }
}
