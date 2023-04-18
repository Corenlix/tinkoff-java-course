package ru.tinkoff.edu.java.scrapper.service.linkupdater;

import linkparser.LinkParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parseresponse.ParseResponse;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.UpdateMessagesSender;
import ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler.LinkHandler;
import ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler.LinkHandlerChain;
import ru.tinkoff.edu.java.scrapper.model.LinkEntity;
import ru.tinkoff.edu.java.scrapper.model.linkcontent.LinkContent;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LinkUpdaterImpl implements LinkUpdater {
    private final UpdateMessagesSender updateMessagesSender;
    private final LinkHandlerChain linkHandlerChain;
    private final LinkParser linkParser;

    public LinkEntity update(LinkEntity link) {
        ParseResponse parseResponse = linkParser.parse(URI.create(link.url()));
        LinkHandler handler = linkHandlerChain.getHandler(parseResponse);
        LinkContent currentContent = handler.getContent(parseResponse);
        sendUpdatesIfAvailable(link, handler, currentContent);

        return new LinkEntity(link.id(), link.url(), currentContent.toJson(), OffsetDateTime.now());
    }

    private void sendUpdatesIfAvailable(LinkEntity link, LinkHandler handler, LinkContent currentContent) {
        if (link.contentJson() != null && !link.contentJson().isEmpty()) {
            LinkContent oldContent = handler.getContentFromJson(link.contentJson());
            if (!oldContent.equals(currentContent)) {
                List<UpdateMessage> updateMessages = handler.getUpdates(currentContent, oldContent);
                updateMessagesSender.sendUpdates(updateMessages, link.url());
            }
        }
    }
}