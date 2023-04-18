package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.model.LinkEntity;

import java.net.URI;
import java.time.Duration;
import java.util.List;

public interface LinkService {
    LinkEntity add(Long chatId, URI url);
    LinkEntity remove(Long chatId, URI url);
    List<LinkEntity> findByChatId(Long chatId);
    List<LinkEntity> findLinksUpdatedBefore(Duration interval);
    void save(LinkEntity linkEntity);
}
