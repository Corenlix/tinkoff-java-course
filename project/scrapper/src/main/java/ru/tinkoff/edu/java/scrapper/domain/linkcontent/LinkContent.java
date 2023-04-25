package ru.tinkoff.edu.java.scrapper.domain.linkcontent;

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

    @SneakyThrows
    static  <T extends LinkContent> T fromJson(String json, Class<T> clazz) {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build()
                .readValue(json, clazz);
    }
}
