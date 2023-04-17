package ru.tinkoff.edu.java.scrapper.httpclient;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import ru.tinkoff.edu.java.scrapper.dto.tgBot.LinkUpdate;

@HttpExchange("/updates")
public interface TgBotClient {
    @PostMapping
    void addUpdate(@RequestBody LinkUpdate linkUpdate);
}