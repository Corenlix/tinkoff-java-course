package ru.tinkoff.edu.bot.test;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.command.StartCommand;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.command.replycommand.TrackCommand;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.commandprocessor.CommandProcessor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommandProcessorTest {
    private CommandProcessor commandProcessor;

    @Mock
    Update update;
    @Mock
    Message message;
    @Mock
    Chat chat;

    @BeforeEach
    void setup() {
        var commands = List.of(new StartCommand(null), new TrackCommand(null));
        commandProcessor = new CommandProcessor(commands);
    }

    @Test
    void whenUnknownCommand_thenReturnUnknownCommandMessage() {
        //given
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/nichego");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);


        //when
        var processResponse = commandProcessor.tryProcess(update);

        //then
        assertThat(processResponse.get().getParameters().get("text")).isEqualTo("Команда неизвестна");
    }
}
