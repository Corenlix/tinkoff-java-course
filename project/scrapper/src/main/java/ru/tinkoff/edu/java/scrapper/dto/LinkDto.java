package ru.tinkoff.edu.java.scrapper.dto;

import java.time.OffsetDateTime;

public record LinkDto (Long id, String url, String contentJson, OffsetDateTime updatedAt) {
}
