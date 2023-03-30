package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command{
    @Override
    public String name() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Прекратить отслеживание посылки";
    }

    @Override
    public SendMessage process(Update update) {
        return new SendMessage(update.message().chat().id(), "Пока не работает :с");
    }
}
