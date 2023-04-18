package ru.tinkoff.edu.java.scrapper.configuration;

import linkparser.LinkParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkParserConfig {
    @Bean
    public LinkParser linkParser() {
        return new LinkParser();
    }
}
