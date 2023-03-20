package ru.tinkoff.edu.java.scrapper.httpclient;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import ru.tinkoff.edu.java.scrapper.dto.GithubRepositoryResponse;

@HttpExchange(url = "/repos/{owner}/{repo}", accept = "application/vnd.github.v3+json")
public interface GitHubClient {
    @GetExchange
    GithubRepositoryResponse fetchRepository(@PathVariable String owner, @PathVariable String repo);
}
