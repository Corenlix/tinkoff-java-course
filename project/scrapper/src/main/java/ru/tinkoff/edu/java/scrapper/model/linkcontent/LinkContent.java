package ru.tinkoff.edu.java.scrapper.model.linkcontent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

public interface LinkContent {
    @SneakyThrows
    default String toJson() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build()
                .writeValueAsString(this);
    }
}
