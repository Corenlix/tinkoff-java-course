package ru.tinkoff.edu.java.bot.bot.updateprocessor.command.replycommand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.tinkoff.edu.java.bot.dto.scrapper.AddLinkRequest;
import ru.tinkoff.edu.java.bot.httpclient.ScrapperClient;

@Component
@RequiredArgsConstructor
public class TrackCommand extends ReplyCommand {
    private final ScrapperClient scrapperClient;

    @Override
    public String name() {
        return "/track";
    }

    @Override
    public String description() {
        return "Начать отслеживание ссылки";
    }

    @Override
    public String replyText() {
        return "Введите ссылку для отслеживания:";
    }

    @Override
    public SendMessage processReply(Update update) {
        String link = update.message().text();
        try {
            AddLinkRequest request = new AddLinkRequest(link);
            scrapperClient.addLink(update.message().chat().id(), request);
        } catch (WebClientResponseException.BadRequest ex) {
            return new SendMessage(update.message().chat().id(), "При добавлении ссылки в список отслеживаемых произошла ошибка :с");
        }

        return new SendMessage(update.message().chat().id(), "Ссылка успешно добавлена в список отслеживаемых с:");
    }
}
