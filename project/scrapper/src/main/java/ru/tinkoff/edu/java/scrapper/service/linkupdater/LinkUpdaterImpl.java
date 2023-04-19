package ru.tinkoff.edu.java.scrapper.service.linkupdater;

import linkparser.LinkParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import parseresponse.ParseResponse;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.model.LinkEntity;
import ru.tinkoff.edu.java.scrapper.model.linkcontent.LinkContent;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.UpdateMessagesSender;
import ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler.LinkHandler;
import ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler.LinkHandlerChain;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkUpdaterImpl implements LinkUpdater {
    private final UpdateMessagesSender updateMessagesSender;
    private final LinkHandlerChain linkHandlerChain;
    private final LinkParser linkParser;

    public LinkEntity update(LinkEntity link) {
        ParseResponse parseResponse = linkParser.parse(URI.create(link.url()));
        LinkHandler handler = linkHandlerChain.getHandler(parseResponse);
        LinkContent currentLinkContent = handler.getContent(parseResponse);
        sendUpdatesIfAvailable(link, handler, currentLinkContent);

        return new LinkEntity(link.id(), link.url(), currentLinkContent.toJson(), OffsetDateTime.now());
    }

    private void sendUpdatesIfAvailable(LinkEntity link, LinkHandler handler, LinkContent currentLinkContent) {
        List<UpdateMessage> updateMessages = getAvailableUpdates(link, handler, currentLinkContent);
        if(!updateMessages.isEmpty()) {
            updateMessagesSender.sendUpdates(updateMessages, link.url());
        }
    }

    private List<UpdateMessage> getAvailableUpdates(LinkEntity link, LinkHandler handler, LinkContent currentLinkContent) {
        if (link.contentJson() != null && !link.contentJson().isEmpty()) {
            LinkContent oldContent = handler.getContentFromJson(link.contentJson());
            if (!oldContent.equals(currentLinkContent)) {
                return handler.getUpdates(currentLinkContent, oldContent);
            }
        }

        return Collections.emptyList();
    }
}