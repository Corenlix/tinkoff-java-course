package ru.tinkoff.edu.java.scrapper.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.time.OffsetDateTime;

public record GitHubRepositoryResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("html_url") URI url,
        @JsonProperty("owner") GitHubUserResponse owner,
        @JsonProperty("language") String language,
        @JsonProperty("size") Long size,
        @JsonProperty("visibility") String visibility,
        @JsonProperty("subscribers_count") Long subscribersCount,
        @JsonProperty("watchers") Integer watchers,
        @JsonProperty("forks") Integer forks,
        @JsonProperty("open_issues") Integer openIssues,
        @JsonProperty("created_at") OffsetDateTime createdAt,
        @JsonProperty("pushed_at") OffsetDateTime pushedAtAt,
        @JsonProperty("updated_at") OffsetDateTime updatedAt) {
}
