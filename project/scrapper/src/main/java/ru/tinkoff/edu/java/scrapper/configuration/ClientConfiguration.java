package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.tinkoff.edu.java.scrapper.httpclient.GithubClient;

@Configuration
public class ClientConfiguration {
    @Value("${github.api.path:https://api.github.com/}")
    private String githubApiPath;

    @Bean
    GithubClient githubClient() {
        WebClient client = WebClient.builder().baseUrl(githubApiPath).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();

        return factory.createClient(GithubClient.class);
    }
}
