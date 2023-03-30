package ru.tinkoff.edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.UpdateProcessorsChain;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.command.Command;

import java.util.List;

@Service
public class TelegramUpdatesListener implements UpdatesListener {
    private final UpdateProcessorsChain updateProcessorsChain;

    public TelegramUpdatesListener(TelegramBot telegramBot, List<Command> commands, UpdateProcessorsChain updateProcessorsChain) {
        this.updateProcessorsChain = updateProcessorsChain;
        telegramBot.setUpdatesListener(this);
        telegramBot.execute(buildSetCommandsRequest(commands));
    }

    @Override
    public int process(List<Update> list) {
        list.forEach(updateProcessorsChain::process);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private SetMyCommands buildSetCommandsRequest(List<Command> commands) {
        BotCommand[] botCommands = commands.stream()
                .map(command -> new BotCommand(command.name(), command.description()))
                .toArray(BotCommand[]::new);
        return new SetMyCommands(botCommands);
    }
}