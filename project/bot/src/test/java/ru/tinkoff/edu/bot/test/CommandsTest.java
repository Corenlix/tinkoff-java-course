package ru.tinkoff.edu.bot.test;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.edu.java.bot.bot.updateprocessor.command.ListCommand;
import ru.tinkoff.edu.java.bot.dto.scrapper.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.scrapper.ListLinksResponse;
import ru.tinkoff.edu.java.bot.httpclient.ScrapperClient;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandsTest {
    @Mock
    ScrapperClient scrapperClient;
    @Mock
    Update update;
    @Mock
    Message message;
    @Mock
    Chat chat;

    @Test
    void listCommandWhenEmpty_shouldReturnSpecialMessageTest() {
        //given
        var command = new ListCommand(scrapperClient);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(scrapperClient.getAllLinks(any())).thenReturn(new ListLinksResponse(null, 0));
        when(chat.id()).thenReturn(1L);

        //when
        var processResponse = command.process(update);

        //then
        assertAll("Assert process response",
                () -> assertThat(processResponse.getParameters().get("text")).isEqualTo("Ничего не отслеживается! Используй /track, чтобы добавить ссылки"));
    }

    @Test
    void listCommandWhenHasElements_thenReturnThem() {
        //given
        var command = new ListCommand(scrapperClient);
        var link = new LinkResponse(1L, URI.create("https://test.com"));
        var link2 = new LinkResponse(2L, URI.create("https://test.com"));
        var linksResponse = new ListLinksResponse(List.of(link, link2), 2);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(scrapperClient.getAllLinks(any())).thenReturn(linksResponse);
        when(chat.id()).thenReturn(1L);

        //when
        var processResponse = command.process(update);

        //then
        assertThat(processResponse.getParameters().get("text")).isEqualTo(link.url().toString() + "\n" + link2.url().toString());
    }

}
