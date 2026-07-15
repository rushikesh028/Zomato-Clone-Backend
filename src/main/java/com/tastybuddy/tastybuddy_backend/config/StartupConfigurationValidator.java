package com.tastybuddy.tastybuddy_backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class StartupConfigurationValidator implements ApplicationRunner {

    static final String DEFAULT_JWT_SECRET = "change-this-jwt-secret-to-a-long-random-value";

    private static final Logger log = LoggerFactory.getLogger(StartupConfigurationValidator.class);

    private final Environment environment;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${spring.datasource.url:}")
    private String datasourceUrl;

    @Value("${razorpay.key:}")
    private String razorpayKey;

    @Value("${razorpay.secret:}")
    private String razorpaySecret;

    public StartupConfigurationValidator(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) {
        validateJwtSecret();
        logPaymentConfiguration();
    }

    private void validateJwtSecret() {
        if (DEFAULT_JWT_SECRET.equals(jwtSecret) && isProductionLikeEnvironment()) {
            throw new IllegalStateException("Set a strong JWT_SECRET before production startup.");
        }

        if (DEFAULT_JWT_SECRET.equals(jwtSecret)) {
            log.warn("Using the default JWT secret for local startup only. Set a strong JWT_SECRET before production startup.");
        }
    }

    private void logPaymentConfiguration() {
        if (isBlank(razorpayKey) || isBlank(razorpaySecret)) {
            log.warn("Payment endpoints are disabled until RAZORPAY_KEY and RAZORPAY_SECRET are configured.");
        }
    }

    private boolean isProductionLikeEnvironment() {
        return hasActiveProfile("prod", "production") || !isH2Datasource();
    }

    private boolean isH2Datasource() {
        return datasourceUrl != null && datasourceUrl.startsWith("jdbc:h2:");
    }

    private boolean hasActiveProfile(String... profiles) {
        return Arrays.stream(environment.getActiveProfiles())
                .map(String::toLowerCase)
                .anyMatch(profile -> Arrays.stream(profiles)
                        .map(String::toLowerCase)
                        .anyMatch(profile::equals));
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
