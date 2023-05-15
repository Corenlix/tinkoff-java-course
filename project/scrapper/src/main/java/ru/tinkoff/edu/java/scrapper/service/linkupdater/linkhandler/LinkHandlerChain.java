package ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import parseresponse.ParseResponse;

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
