package ru.tinkoff.edu.java.bot.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HelpCommand implements Command {
    private final List<Command> commands;

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
        this.commands.add(this);
    }

    @Override
    public String name() {
        return "/help";
    }

    @Override
    public String description() {
        return "Список команд";
    }

    @Override
    public SendMessage process(Update update) {
        return new SendMessage(update.message().chat().id(), getCommandsDescription());
    }

    private String getCommandsDescription() {
        StringBuilder sb = new StringBuilder();
        commands.forEach(command -> {
            sb.append(command.name());
            sb.append(" — ");
            sb.append(command.description());
            sb.append("\n");
        });
        return sb.toString();
    }
}