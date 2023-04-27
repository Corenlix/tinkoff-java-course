package ru.tinkoff.edu.java.scrapper.httpclient;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.tinkoff.edu.java.scrapper.dto.client.tgBot.LinkUpdate;

@HttpExchange(url = "/updates")
public interface TgBotClient {
    @PostExchange
    void addUpdate(@RequestBody LinkUpdate linkUpdate);
}