package com.tastybuddy.tastybuddy_backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tastybuddy.tastybuddy_backend.dto.LoginRequest;
import com.tastybuddy.tastybuddy_backend.dto.RegisterRequest;
import com.tastybuddy.tastybuddy_backend.entity.FoodItem;
import com.tastybuddy.tastybuddy_backend.repository.FoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TastybuddyBackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FoodRepository foodRepository;

    @Test
    void healthEndpointIsPublic() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void rootServesFrontend() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("index.html"));

        mockMvc.perform(get("/index.html"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TastyBuddy")))
                .andExpect(content().string(containsString("Food Ordering Workspace")));
    }

    @Test
    void staticAssetsArePublic() throws Exception {
        mockMvc.perform(get("/app.js"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("const state")));
    }

    @Test
    void foodsEndpointIsPublic() throws Exception {
        foodRepository.save(FoodItem.builder()
                .name("Test Pasta")
                .price(199)
                .category("VEG")
                .imageUrl("https://example.com/pasta.jpg")
                .build());

        mockMvc.perform(get("/api/foods"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/foods/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("VEG"));
    }

    @Test
    void openApiDocsArePublic() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("TastyBuddy Backend API"));
    }

    @Test
    void registerAndLoginFlowReturnsJwt() throws Exception {
        registerUser("testuser@example.com", "Password123");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("testuser@example.com");
        loginRequest.setPassword("Password123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.user.email").value("testuser@example.com"));
    }

    @Test
    void authenticatedCartAndOrderFlowWorks() throws Exception {
        FoodItem food = foodRepository.save(FoodItem.builder()
                .name("Paneer Bowl")
                .price(249)
                .category("VEG")
                .imageUrl("https://example.com/paneer-bowl.jpg")
                .build());

        registerUser("cartuser@example.com", "Password123");
        String token = loginAndGetToken("cartuser@example.com", "Password123");

        MvcResult addResult = mockMvc.perform(post("/api/cart")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"foodId": %d, "quantity": 2}
                                """.formatted(food.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.foodId").value(food.getId()))
                .andReturn();

        JsonNode cartNode = objectMapper.readTree(addResult.getResponse().getContentAsString());
        long cartItemId = cartNode.get("id").asLong();

        mockMvc.perform(put("/api/cart/{id}", cartItemId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"quantity": 3}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(3));

        MvcResult orderResult = mockMvc.perform(post("/api/orders")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PLACED"))
                .andExpect(jsonPath("$.items[0].foodId").value(food.getId()))
                .andReturn();

        long orderId = objectMapper.readTree(orderResult.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(get("/api/orders/{id}", orderId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId));

        mockMvc.perform(delete("/api/cart")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Cart cleared successfully"));
    }

    private void registerUser(String email, String password) throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("Test User");
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    private String loginAndGetToken(String email, String password) throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readTree(result.getResponse().getContentAsString()).get("token").asText();
    }
}
