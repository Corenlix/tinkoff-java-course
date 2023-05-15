package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import java.net.URI;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull String test,
        @NotNull String telegramToken,
        @NotNull URI scrapperPath,
        @NotNull String rabbitQueueName,
        @NotNull String rabbitRoutingKey,
        @NotNull String rabbitExchangeName,
        @NotNull String micrometerProcessedMessagesCounter
) {
}
