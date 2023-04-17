package ru.tinkoff.edu.java.scrapper.dto.controller.links;

import java.util.List;

public record ListLinksResponse(List<LinkResponse> items, Integer size) {
}
