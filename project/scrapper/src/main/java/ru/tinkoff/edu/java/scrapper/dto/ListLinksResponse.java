package ru.tinkoff.edu.java.scrapper.dto;

import java.util.List;

public record ListLinksResponse(List<LinkResponse> items, Integer size) {
}
