package ru.tinkoff.edu.java.scrapper.model;

import java.time.OffsetDateTime;

public record LinkEntity(Long id, String url, String contentJson, OffsetDateTime updatedAt) {
}
