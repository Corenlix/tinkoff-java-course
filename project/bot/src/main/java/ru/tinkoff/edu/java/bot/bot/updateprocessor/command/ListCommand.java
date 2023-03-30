package ru.tinkoff.edu.java.bot.bot.updateprocessor.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.scrapper.ListLinksResponse;
import ru.tinkoff.edu.java.bot.httpclient.ScrapperClient;

@Component
@RequiredArgsConstructor
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
        ListLinksResponse response = scrapperClient.getAllLinks(update.message().chat().id());
        if (response.size() == 0) {
            return new SendMessage(update.message().chat().id(), "Ничего не отслеживается! Используй /track, чтобы добавить ссылки");
        } else {
            String[] links = response.items().stream().map(linkResponse -> linkResponse.url().toString()).toArray(String[]::new);
            return new SendMessage(update.message().chat().id(), String.join("/n", links));
        }
    }
}
