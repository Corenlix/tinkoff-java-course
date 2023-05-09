package ru.tinkoff.edu.java.bot.bot.updateprocessor.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    String name();

    String description();

    SendMessage process(Update update);
}
