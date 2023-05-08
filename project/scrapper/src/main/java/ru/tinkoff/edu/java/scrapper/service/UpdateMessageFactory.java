package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.ChatEntity;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.dto.client.tgBot.LinkUpdate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateMessageFactory {
    private final ChatService chatService;

    public LinkUpdate getLinkUpdate(List<UpdateMessage> updateMessages, String url) {
        List<Long> chatIds = chatService
                .findByLink(url).stream()
                .map(ChatEntity::id)
                .toList();

        String updatesMessage =
                String.join("\n", updateMessages.stream().map(UpdateMessage::changesMessage).toArray(String[]::new));

        return new LinkUpdate(1L, url, updatesMessage, chatIds);
    }
}
