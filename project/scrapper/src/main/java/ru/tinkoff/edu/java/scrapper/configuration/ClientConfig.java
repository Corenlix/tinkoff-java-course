package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
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
    @Value("${github-api.path:https://api.github.com/}")
    private String gitHubApiPath;

    @Value("${stack-exchange-api.path:https://api.stackexchange.com/}")
    private String stackExchangeApiPath;

    @Value("${stack-overflow-api.version:2.3}")
    private String stackOverflowApiVersion;

    @Bean
    GitHubClient gitHubClient() {
        WebClient client = WebClient.builder()
                .baseUrl(gitHubApiPath)
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(GitHubClient.class);
    }

    @Bean
    StackOverflowClient stackOverflowClient() {
        WebClient client = WebClient.builder()
                .baseUrl(stackExchangeApiPath)
                .build();

        return StackOverflowClientImpl.builder()
                .webClient(client)
                .apiVersion(stackOverflowApiVersion)
                .build();
    }
}
