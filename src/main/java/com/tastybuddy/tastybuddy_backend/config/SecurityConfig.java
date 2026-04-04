package com.tastybuddy.tastybuddy_backend.config;



import com.tastybuddy.tastybuddy_backend.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // ✅ PUBLIC APIs
                        .requestMatchers("/api/users/register", "/api/auth/login").permitAll()

                        // 👑 ADMIN APIs
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 🛒 CART
                        .requestMatchers("/api/cart/**").authenticated()

                        // 🍔 FOODS
                        .requestMatchers("/api/foods/**").authenticated()

                        // 📦 ORDERS
                        .requestMatchers("/api/orders/**").authenticated()

                        // 🔥 MUST BE LAST (ONLY ONCE)
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
