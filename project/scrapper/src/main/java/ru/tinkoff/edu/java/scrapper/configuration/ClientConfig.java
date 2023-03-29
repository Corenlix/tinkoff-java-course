package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.tinkoff.edu.java.scrapper.httpclient.GitHubClient;
import ru.tinkoff.edu.java.scrapper.httpclient.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.httpclient.StackOverflowClientImpl;

@Configuration
public class ClientConfig {
    @Bean
    GitHubClient gitHubClient(ApplicationConfig applicationConfig) {
        WebClient client = WebClient.builder()
                .baseUrl(applicationConfig.githubApiPath().toString())
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(GitHubClient.class);
    }

    @Bean
    StackOverflowClient stackOverflowClient(ApplicationConfig applicationConfig) {
        WebClient client = WebClient.builder()
                .baseUrl(applicationConfig.stackExchangeApiPath().toString())
                .build();

        return StackOverflowClientImpl.builder()
                .webClient(client)
                .apiVersion(applicationConfig.stackOverflowApiVersion())
                .build();
    }
}
