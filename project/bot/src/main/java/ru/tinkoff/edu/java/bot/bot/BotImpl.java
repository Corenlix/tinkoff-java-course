package ru.tinkoff.edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.bot.Bot.command.Command;
import ru.tinkoff.edu.java.bot.bot.command.Command;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.UpdateProcessor;

import java.util.List;


@Service
public class BotImpl implements Bot, AutoCloseable {
    private final UpdateProcessor updateProcessor;
    private final TelegramBot telegramBot;

    public BotImpl(TelegramBot telegramBot, UpdateProcessor updateProcessor, List<Command> commands) {
        this.updateProcessor = updateProcessor;
        this.telegramBot = telegramBot;
        this.telegramBot.setUpdatesListener(this);
        this.telegramBot.execute(buildSetCommandsRequest(commands));
    }

    @Override
    public int process(List<Update> list) {
        list.forEach(updateProcessor::process);
        return list.get(list.size() - 1).updateId();
    }

    @Override
    public void close() {
        telegramBot.removeGetUpdatesListener();
    }

    private SetMyCommands buildSetCommandsRequest(List<Command> commands) {
        BotCommand[] botCommands = commands.stream()
                .map(command -> new BotCommand(command.name(), command.description()))
                .toArray(BotCommand[]::new);
        return new SetMyCommands(botCommands);
    }
}