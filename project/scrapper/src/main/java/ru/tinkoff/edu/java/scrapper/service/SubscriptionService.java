package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.dto.ChatDto;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;

import java.net.URI;
import java.util.List;

public interface SubscriptionService {
    LinkDto subscribe(Long chatId, URI url);
    LinkDto unsubscribe(Long chatId, URI url);
    List<LinkDto> findLinksByChatId(Long chatId);
    List<ChatDto> findChatsByLinkId(Long linkId);
}
