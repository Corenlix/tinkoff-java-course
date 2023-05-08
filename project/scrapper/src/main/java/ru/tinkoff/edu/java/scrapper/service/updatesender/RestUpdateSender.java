package ru.tinkoff.edu.java.scrapper.service.updatesender;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.client.tgBot.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.httpclient.TgBotClient;

@RequiredArgsConstructor
@Service
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class RestUpdateSender implements UpdateSender {
    private final TgBotClient tgBotClient;

    @Override
    public void sendUpdate(LinkUpdate update) {
        tgBotClient.addUpdate(update);
    }
}
