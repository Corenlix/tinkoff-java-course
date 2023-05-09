package ru.tinkoff.edu.java.bot.bot.updateprocessor.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.tinkoff.edu.java.bot.dto.scrapper.ListLinksResponse;
import ru.tinkoff.edu.java.bot.httpclient.ScrapperClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class ListCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String name() {
        return "/list";
    }

    @Override
    public String description() {
        return "Показать список отслеживаемых ссылок";
    }

    @Override
    public SendMessage process(Update update) {
        try {
            ListLinksResponse response = scrapperClient.getAllLinks(update.message().chat().id());
            if (response.size() == 0) {
                return new SendMessage(update.message().chat().id(),
                        "Ничего не отслеживается! Используй /track, чтобы добавить ссылки");
            } else {
                String[] links = response.items().stream()
                        .map(linkResponse -> linkResponse.url().toString()).toArray(String[]::new);
                return new SendMessage(update.message().chat().id(), String.join("\n", links));
            }
        } catch (WebClientResponseException ex) {
            log.error("Ошибка при получении списка подписок!", ex);
            return new SendMessage(update.message().chat().id(), "Ошибка при получении списка подписок :с");
        }
    }
}
