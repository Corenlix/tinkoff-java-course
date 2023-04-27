package ru.tinkoff.edu.java.scrapper.domain;

import java.time.OffsetDateTime;

public record LinkEntity(Long id, String url, String contentJson, OffsetDateTime updatedAt) {
}
