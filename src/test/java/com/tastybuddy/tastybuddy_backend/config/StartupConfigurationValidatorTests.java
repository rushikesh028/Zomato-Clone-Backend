package com.tastybuddy.tastybuddy_backend.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StartupConfigurationValidatorTests {

    @Test
    void allowsDefaultJwtSecretForLocalH2Startup() {
        StartupConfigurationValidator validator = new StartupConfigurationValidator(new MockEnvironment());
        configureValidator(
                validator,
                StartupConfigurationValidator.DEFAULT_JWT_SECRET,
                "jdbc:h2:mem:tastybuddy-test",
                "",
                ""
        );

        assertDoesNotThrow(() -> validator.run(new DefaultApplicationArguments(new String[0])));
    }

    @Test
    void rejectsDefaultJwtSecretForProductionLikeStartup() {
        StartupConfigurationValidator validator = new StartupConfigurationValidator(new MockEnvironment());
        configureValidator(
                validator,
                StartupConfigurationValidator.DEFAULT_JWT_SECRET,
                "jdbc:mysql://localhost:3306/tastybuddy",
                "",
                ""
        );

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> validator.run(new DefaultApplicationArguments(new String[0]))
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                "Set a strong JWT_SECRET before production startup.",
                exception.getMessage()
        );
    }

    private void configureValidator(StartupConfigurationValidator validator,
                                    String jwtSecret,
                                    String datasourceUrl,
                                    String razorpayKey,
                                    String razorpaySecret) {
        ReflectionTestUtils.setField(validator, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(validator, "datasourceUrl", datasourceUrl);
        ReflectionTestUtils.setField(validator, "razorpayKey", razorpayKey);
        ReflectionTestUtils.setField(validator, "razorpaySecret", razorpaySecret);
    }
}
