package ru.tinkoff.edu.java.scrapper.service;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;

public interface LinkService {
    LinkEntity add(Long chatId, URI url);

    LinkEntity remove(Long chatId, URI url);

    List<LinkEntity> findByChatId(Long chatId);

    List<LinkEntity> findLinksUpdatedBefore(OffsetDateTime dateTime);

    void save(LinkEntity linkEntity);
}
