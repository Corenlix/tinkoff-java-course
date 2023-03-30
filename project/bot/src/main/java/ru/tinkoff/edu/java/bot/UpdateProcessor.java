package ru.tinkoff.edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.command.Command;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UpdateProcessor {
    private final TelegramBot telegramBot;
    private final List<Command> commands;

    public void process(Update update) {
        boolean updateWasProcessed = false;
        for (Command command : commands) {
            if (command.canProcess(update)) {
                telegramBot.execute(command.process(update));
                updateWasProcessed = true;
                break;
            }
        }

        if(!updateWasProcessed) {
            telegramBot.execute(getUnknownCommandMessage(update));
        }
    }

    private SendMessage getUnknownCommandMessage(Update update) {
        return new SendMessage(update.message().chat().id(), "Команда неизвестна");
    }
}
