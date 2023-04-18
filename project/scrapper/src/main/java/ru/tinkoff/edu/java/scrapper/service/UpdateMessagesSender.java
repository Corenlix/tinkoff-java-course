package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.dto.client.tgBot.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.httpclient.TgBotClient;
import ru.tinkoff.edu.java.scrapper.model.ChatEntity;

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

        String updatesMessage =
                String.join("\n", updateMessages.stream().map(UpdateMessage::changesMessage).toArray(String[]::new));

        tgBotClient.addUpdate(new LinkUpdate(1L, url, updatesMessage, chatIds));
    }
}