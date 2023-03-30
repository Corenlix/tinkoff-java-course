package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    String name();
    String description();
    SendMessage process(Update update);
    default boolean canProcess(Update update) {
        return update.message().text().equals(name());
    }
}
