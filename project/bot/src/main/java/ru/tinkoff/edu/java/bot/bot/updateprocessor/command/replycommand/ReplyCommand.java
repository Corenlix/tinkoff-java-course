package ru.tinkoff.edu.java.bot.bot.updateprocessor.command.replycommand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.command.Command;

public abstract class ReplyCommand implements Command {
    public abstract String replyText();

    public abstract SendMessage processReply(Update update);

    public SendMessage process(Update update) {
        return new SendMessage(update.message().chat().id(), replyText())
                .replyMarkup(new ForceReply());
    }

    public boolean canProcessReply(Update update) {
        return update.message().replyToMessage() != null
                && update.message().replyToMessage().text().equals(replyText());
    }
}
