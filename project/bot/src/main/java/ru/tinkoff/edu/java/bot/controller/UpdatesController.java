package ru.tinkoff.edu.java.bot.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;

@RestController
@RequestMapping("/updates")
@RequiredArgsConstructor
public class UpdatesController {
    private final TelegramBot telegramBot;

    @Operation(summary = "Отправить обновление", responses = {
            @ApiResponse(responseCode = "200", description = "Обновление обработано"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)) })})
    @PostMapping
    public void addUpdate(@RequestBody LinkUpdate linkUpdate) {
        for (Long tgChatId : linkUpdate.tgChatIds()) {
            telegramBot.execute(new SendMessage(tgChatId, String.format("Новые обновления для ссылки %s:\n%s", linkUpdate.url(), linkUpdate.description())));
        }
    }
}
