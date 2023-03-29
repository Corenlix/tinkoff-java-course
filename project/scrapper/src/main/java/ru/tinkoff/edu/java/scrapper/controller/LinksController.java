package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.*;
import ru.tinkoff.edu.java.scrapper.dto.links.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.links.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.links.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.tgchat.AddLinkRequest;

import java.util.Collections;

@RestController
@RequestMapping("/links")
public class LinksController {


    @Operation(summary ="Получить все отслеживаемые ссылки", responses = {
            @ApiResponse(responseCode = "200", description = "Ссылки успешно получены", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ListLinksResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})})
    @GetMapping
    public ListLinksResponse getAllLinks(@RequestHeader(value="Tg-Chat-Id") Long tgChatId) {
        return new ListLinksResponse(Collections.emptyList(), 0);
    }

    @Operation(summary = "Добавить отслеживание ссылки", responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})})
    @PostMapping
    public LinkResponse addLink(@RequestHeader(value="Tg-Chat-Id") Long tgChatId, @RequestBody AddLinkRequest request) {
        return new LinkResponse(0L, request.link());
    }

    @Operation(summary ="Убрать отслеживание ссылки", responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Ссылка не найдена", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})})
    @DeleteMapping
    public LinkResponse removeLink(@RequestHeader(value="Tg-Chat-Id") Long tgChatId, @RequestBody RemoveLinkRequest request) {
        return new LinkResponse(0L, request.link());
    }
}
