package ru.tinkoff.edu.java.bot.bot.updateprocessor.replycommandprocessor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.UpdateProcessor;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.command.replycommand.ReplyCommand;

import java.util.List;
import java.util.Optional;

@Component
@Order(2)
@RequiredArgsConstructor
public class ReplyCommandProcessor implements UpdateProcessor {
    private final List<ReplyCommand> replyCommands;

    @Override
    public Optional<SendMessage> tryProcess(Update update) {
        if (!isReply(update))
            return Optional.empty();

        for (ReplyCommand replyCommand : replyCommands) {
            if(replyCommand.canProcessReply(update)) {
                return Optional.of(replyCommand.processReply(update));
            }
        }

        return Optional.empty();
    }

    private boolean isReply(Update update) {
        return update.message().replyToMessage() != null
                && update.message().replyToMessage().from().isBot();
    }
}
