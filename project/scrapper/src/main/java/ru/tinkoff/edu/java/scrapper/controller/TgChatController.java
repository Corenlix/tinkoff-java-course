package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;

@RestController
@RequestMapping("/tg-chat/{id}")
public class TgChatController {

    @Operation(summary ="Зарегистрировать чат", responses = {
            @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})})
    @PostMapping
    public void registerChat(@PathVariable Long id) {

    }

    @Operation(summary ="Удалить чат", responses = {
            @ApiResponse(responseCode = "200", description = "Чат успешно удалён"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Чат не существует", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})})
    @DeleteMapping
    public void removeChat(@PathVariable Long id){

    }
}
