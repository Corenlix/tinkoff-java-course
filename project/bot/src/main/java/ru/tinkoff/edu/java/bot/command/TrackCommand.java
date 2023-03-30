package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    @Override
    public String name() {
        return "/track";
    }

    @Override
    public String description() {
        return "Начать отслеживание ссылки";
    }

    @Override
    public SendMessage process(Update update) {
        return new SendMessage(update.message().chat().id(), "Пока не работает :с");
    }
}
