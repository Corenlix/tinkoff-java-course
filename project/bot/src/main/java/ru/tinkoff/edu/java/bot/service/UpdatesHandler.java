package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;

@Service
@RequiredArgsConstructor
public class UpdatesHandler {
    private final TelegramBot telegramBot;

    public void handleUpdate(LinkUpdate linkUpdate) {
        for (Long tgChatId : linkUpdate.tgChatIds()) {
            telegramBot.execute(new SendMessage(tgChatId, String.format("Новые обновления для ссылки %s:\n %s", linkUpdate.url(), linkUpdate.description())));
        }
    }
}
