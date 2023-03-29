package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    @Bean
    long schedulerIntervalMs(ApplicationConfig applicationConfig) {
        return applicationConfig.schedulerInterval().toMillis();
    }
}
