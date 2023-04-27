package ru.tinkoff.edu.java.scrapper.dto.client.stackexchange;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public record StackExchangeQuestionResponse(
        @JsonProperty("title") String title,
        @JsonProperty("tags") List<String> tags,
        @JsonProperty("is_answered") Boolean isAnswered,
        @JsonProperty("view_count") Long viewCount,
        @JsonProperty("answer_count") Integer answerCount,
        @JsonProperty("score") Integer score,
        @JsonProperty("last_edit_time") OffsetDateTime lastEditTime,
        @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate,
        @JsonProperty("protected_date") OffsetDateTime protectedDate,
        @JsonProperty("question_id") Long questionId,
        @JsonProperty("link") URI link,
        @JsonProperty("content_license") String contentLicense
) {
}
