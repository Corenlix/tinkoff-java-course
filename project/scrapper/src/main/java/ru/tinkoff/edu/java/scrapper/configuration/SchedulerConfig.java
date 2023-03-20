package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.scheduler.SchedulerInterval;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class SchedulerConfig {

    @Bean
    SchedulerInterval schedulerInterval(@Value("${scheduler.interval}") String interval) {
        return new SchedulerInterval(Duration.of(Long.parseLong(interval), ChronoUnit.SECONDS));
    }
}
