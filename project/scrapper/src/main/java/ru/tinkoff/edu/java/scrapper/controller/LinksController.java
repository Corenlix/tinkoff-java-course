package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;
import ru.tinkoff.edu.java.scrapper.dto.controller.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.dto.controller.links.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.controller.links.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.controller.links.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.controller.tgchat.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.mapper.LinkMapper;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinksController {
    private final LinkService linkService;
    private final LinkMapper linkMapper;

    @Operation(summary = "Получить все отслеживаемые ссылки", responses = {
            @ApiResponse(responseCode = "200", description = "Ссылки успешно получены",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListLinksResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})})
    @GetMapping
    public ListLinksResponse getAllLinks(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId) {
        List<LinkEntity> links = linkService.findByChatId(tgChatId);
        return linkMapper.toListLinksResponse(links);
    }

    @Operation(summary = "Добавить отслеживание ссылки", responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LinkResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})})
    @PostMapping
    public LinkResponse addLink(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId,
                                @RequestBody AddLinkRequest request) {
        LinkEntity link = linkService.add(tgChatId, request.link());
        return linkMapper.toLinkResponse(link);
    }

    @Operation(summary = "Убрать отслеживание ссылки", responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LinkResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Ссылка не найдена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})})
    @DeleteMapping
    public LinkResponse removeLink(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId,
                                   @RequestBody RemoveLinkRequest request) {
        LinkEntity linkEntity = linkService.remove(tgChatId, request.link());
        return linkMapper.toLinkResponse(linkEntity);
    }
}
