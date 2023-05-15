package ru.tinkoff.edu.java.bot.bot.updateprocessor.commandprocessor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.UpdateProcessor;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.command.Command;

@Component
@Order(1)
public class CommandProcessor implements UpdateProcessor {
    private final Map<String, Command> commands;

    public CommandProcessor(List<Command> commands) {
        this.commands = commands.stream().collect(Collectors.toMap(Command::name, command -> command));
    }

    public Optional<SendMessage> tryProcess(Update update) {
        if (!isCommand(update)) {
            return Optional.empty();
        }

        Command command = commands.get(update.message().text());
        SendMessage message = command != null ? command.process(update) : getUnknownCommandMessage(update);

        return Optional.of(message);
    }

    private boolean isCommand(Update update) {
        return update.message().text().startsWith("/");
    }

    private SendMessage getUnknownCommandMessage(Update update) {
        return new SendMessage(update.message().chat().id(), "Команда неизвестна");
    }
}
