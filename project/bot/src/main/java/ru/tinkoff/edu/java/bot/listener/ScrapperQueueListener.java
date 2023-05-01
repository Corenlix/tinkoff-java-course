package ru.tinkoff.edu.java.bot.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;
import ru.tinkoff.edu.java.bot.service.UpdatesHandler;

@Component
@RabbitListener(queues = "${app.rabbit-queue-name}")
@RequiredArgsConstructor
public class ScrapperQueueListener {
    private final UpdatesHandler updatesHandler;

    @RabbitHandler
    public void receiver(LinkUpdate linkUpdate) {
        updatesHandler.handleUpdate(linkUpdate);
    }
}