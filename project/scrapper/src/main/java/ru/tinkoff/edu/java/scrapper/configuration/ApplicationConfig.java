package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull URI githubApiPath,
                                @NotNull URI stackExchangeApiPath,
                                @NotNull String stackOverflowApiVersion,
                                @NotNull Duration schedulerInterval,
                                @NotNull Duration linkUpdateInterval,
                                @NotNull URI tgBotPath,
                                @NotNull AccessType databaseAccessType,
                                @NotNull String rabbitQueueName,
                                @NotNull String rabbitRoutingKey,
                                @NotNull String rabbitExchangeName,
                                @NotNull boolean useQueue
                                ) {
}