package ru.tinkoff.edu.bot.test;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.command.StartCommand;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.command.replycommand.TrackCommand;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.commandprocessor.CommandProcessor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandProcessorTest {
    private CommandProcessor commandProcessor;

    @BeforeEach
    void setup() {
        var commands = List.of(new StartCommand(null), new TrackCommand(null));
        commandProcessor = new CommandProcessor(commands);
    }

    @Test
    void whenUnknownCommand_thenReturnUnknownCommandMessage() {
        //given
        var update = new Update();
        var message = new Message();
        var chat = new Chat();
        ReflectionTestUtils.setField(update, "message", message);
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(message, "text", "/nichego");
        ReflectionTestUtils.setField(chat, "id", 1L);

        //when
        var processResponse = commandProcessor.tryProcess(update);

        //then
        assertThat(processResponse.get().getParameters().get("text")).isEqualTo("Команда неизвестна");
    }
}
