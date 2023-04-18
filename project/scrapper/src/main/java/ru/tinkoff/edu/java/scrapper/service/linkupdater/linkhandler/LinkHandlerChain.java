package ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import parseresponse.ParseResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LinkHandlerChain {
    private final List<LinkHandler> linkHandlers;

    public LinkHandler getHandler(ParseResponse parseResponse) {
        return linkHandlers.stream()
                .filter(handler -> handler.canHandle(parseResponse))
                .findFirst().orElseThrow();
    }
}
