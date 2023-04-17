package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.model.ChatEntity;
import ru.tinkoff.edu.java.scrapper.model.LinkEntity;

import java.net.URI;
import java.util.List;

public interface SubscriptionService {
    LinkEntity subscribe(Long chatId, URI url);
    LinkEntity unsubscribe(Long chatId, URI url);
    List<LinkEntity> findLinksByChatId(Long chatId);
    List<ChatEntity> findChatsByLinkId(Long linkId);
}
