package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;

public record StackExchangeQuestionResponse(String title, List<String> tags, @JsonProperty("last_edit_time") OffsetDateTime lastEditTime) {
}
