package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.dto.client.tgBot.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.httpclient.TgBotClient;
import ru.tinkoff.edu.java.scrapper.domain.ChatEntity;

import java.util.List;

@Service
public class UpdateMessagesSender {
    private final ScrapperQueueProducer scrapperQueueProducer;
    private final TgBotClient tgBotClient;
    private final ChatService chatService;
    private final boolean useQueue;

    public UpdateMessagesSender(ScrapperQueueProducer scrapperQueueProducer, TgBotClient tgBotClient, ChatService chatService, ApplicationConfig applicationConfig) {
        this.scrapperQueueProducer = scrapperQueueProducer;
        this.tgBotClient = tgBotClient;
        this.chatService = chatService;
        this.useQueue = applicationConfig.useQueue();
    }

    public void sendUpdates(List<UpdateMessage> updateMessages, String url) {
        LinkUpdate linkUpdate = getLinkUpdate(updateMessages, url);
        if (useQueue) {
            scrapperQueueProducer.sendUpdate(linkUpdate);
        } else {
            tgBotClient.addUpdate(linkUpdate);
        }
    }

    @NotNull
    private LinkUpdate getLinkUpdate(List<UpdateMessage> updateMessages, String url) {
        List<Long> chatIds = chatService
                .findByLink(url).stream()
                .map(ChatEntity::id)
                .toList();

        String updatesMessage =
                String.join("\n", updateMessages.stream().map(UpdateMessage::changesMessage).toArray(String[]::new));

        return new LinkUpdate(1L, url, updatesMessage, chatIds);
    }
}