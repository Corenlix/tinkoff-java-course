package ru.tinkoff.edu.java.scrapper.service;

import linkparser.LinkParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parseresponse.ParseResponse;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.linkhandler.LinkHandler;
import ru.tinkoff.edu.java.scrapper.linkhandler.LinkHandlerChain;
import ru.tinkoff.edu.java.scrapper.model.LinkEntity;
import ru.tinkoff.edu.java.scrapper.model.linkcontent.LinkContent;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkUpdaterImpl implements LinkUpdater {
    private final UpdateMessagesSender updateMessagesSender;
    private final LinkHandlerChain linkHandlerChain;
    private final LinkParser linkParser;

    public LinkEntity update(LinkEntity link) {
        ParseResponse parseResponse = linkParser.parse(URI.create(link.url()));
        LinkHandler handler = linkHandlerChain.getHandler(parseResponse);
        LinkContent content = handler.getContent(parseResponse);
        List<UpdateMessage> updatesMessages = handler.getUpdates(content, link.contentJson());
        updateMessagesSender.sendUpdates(updatesMessages, link.url());

        return new LinkEntity(link.id(), link.url(), content.toJson(), OffsetDateTime.now());
    }
}