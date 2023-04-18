package ru.tinkoff.edu.java.bot.bot.updateprocessor.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.tinkoff.edu.java.bot.httpclient.ScrapperClient;


@Component
@RequiredArgsConstructor
@Slf4j
public class StartCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String name() {
        return "/start";
    }

    @Override
    public String description() {
        return "Регистрация";
    }

    @Override
    public SendMessage process(Update update) {
        try {
            scrapperClient.registerChat(update.message().chat().id());
        } catch (WebClientResponseException.BadRequest exception) {
            log.error("Ошибка при добавлении чата!", exception);
        }

        return new SendMessage(update.message().chat().id(), "Привет! Напиши /help, чтобы получить список команд");
    }
}
