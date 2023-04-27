package ru.tinkoff.edu.java.bot.bot.updateprocessor.command.replycommand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.tinkoff.edu.java.bot.dto.scrapper.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.httpclient.ScrapperClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class UntrackCommand extends ReplyCommand {
    private final ScrapperClient scrapperClient;

    @Override
    public String name() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Прекратить отслеживание посылки";
    }

    @Override
    public String replyText() {
        return "Введите ссылку для удаления:";
    }

    @Override
    public SendMessage processReply(Update update) {
        String link = update.message().text();
        RemoveLinkRequest request = new RemoveLinkRequest(link);
        try {
            scrapperClient.removeLink(update.message().chat().id(), request);
        }
        catch (WebClientResponseException.NotFound ex) {
            log.error("Ссылка для удаления не отслеживается пользователем!", ex);
            return new SendMessage(update.message().chat().id(), "Ссылка не найдена :с");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.error("Ошибка при добавлении ссылки в список отслеживаемых!", ex);
            return new SendMessage(update.message().chat().id(), "При удалении произошла ошибка :с");
        }
        return new SendMessage(update.message().chat().id(), "Ссылка успешно удалена с:");
    }
}
