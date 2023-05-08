package ru.tinkoff.edu.java.scrapper.service.updatesender;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.client.tgBot.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.service.ScrapperQueueProducer;

@RequiredArgsConstructor
@Service
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class RabbitUpdateSender implements UpdateSender {
    private final ScrapperQueueProducer scrapperQueueProducer;

    @Override
    public void sendUpdate(LinkUpdate update) {
        scrapperQueueProducer.sendUpdate(update);
    }
}
