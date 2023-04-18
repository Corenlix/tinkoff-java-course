package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.dto.client.tgBot.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.httpclient.TgBotClient;
import ru.tinkoff.edu.java.scrapper.model.ChatEntity;
import ru.tinkoff.edu.java.scrapper.repository.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.JdbcLinkRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateMessagesSender {
    private final TgBotClient tgBotClient;
    private final ChatService chatService;

    public void sendUpdates(List<UpdateMessage> updateMessages, String url) {
        List<Long> chatIds = chatService
                .findByLink(url).stream()
                .map(ChatEntity::id)
                .toList();

        updateMessages.stream()
                .map(updateMessage -> new LinkUpdate(0L, url, updateMessage.changesMessage(), chatIds))
                .forEach(tgBotClient::addUpdate);
    }
}