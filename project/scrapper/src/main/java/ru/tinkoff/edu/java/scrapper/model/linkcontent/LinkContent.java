package ru.tinkoff.edu.java.scrapper.model.linkcontent;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public interface LinkContent {
    @SneakyThrows
    default String toJson() {
        return new ObjectMapper().writeValueAsString(this);
    }
}
