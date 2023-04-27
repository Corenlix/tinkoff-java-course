package ru.tinkoff.edu.java.scrapper.dto.client.github;

import java.net.URI;

public record GitHubUserResponse(String login, Long id, URI url) {
}
