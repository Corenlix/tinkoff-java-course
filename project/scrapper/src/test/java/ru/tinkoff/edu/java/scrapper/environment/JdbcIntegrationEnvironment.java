package ru.tinkoff.edu.java.scrapper.environment;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class JdbcIntegrationEnvironment extends IntegrationEnvironment {

    @DynamicPropertySource
    static void accessProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jdbc");
    }
}
