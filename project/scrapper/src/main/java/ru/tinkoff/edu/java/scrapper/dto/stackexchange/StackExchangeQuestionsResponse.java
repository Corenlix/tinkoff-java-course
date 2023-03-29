package ru.tinkoff.edu.java.scrapper.dto.stackexchange;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record StackExchangeQuestionsResponse(@JsonProperty("items") List<StackExchangeQuestionResponse> questions) {
}
