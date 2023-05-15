package ru.tinkoff.edu.java.scrapper.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.dto.client.tgBot.LinkUpdate;

@Service
public class ScrapperQueueProducer {
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;

    public ScrapperQueueProducer(RabbitTemplate rabbitTemplate, ApplicationConfig config) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = config.rabbitExchangeName();
        this.routingKey = config.rabbitRoutingKey();
    }

    public void sendUpdate(LinkUpdate update) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, update);
    }
}
