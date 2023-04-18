package ru.tinkoff.edu.java.scrapper.controller.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.dto.controller.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exception.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;


@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(value = {LinkNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse linkNotFound(LinkNotFoundException ex) {
        log.info("Ссылка не найдена!", ex);
        return new ApiErrorResponse("Ссылка не найдена", HttpStatus.NOT_FOUND.toString(), ex);
    }

    @ExceptionHandler(value = {ChatNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse linkNotFound(ChatNotFoundException ex) {
        log.info("Чат не существует!", ex);
        return new ApiErrorResponse("Чат не существует", HttpStatus.NOT_FOUND.toString(), ex);
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse serverError(Exception ex) {
        log.error("Ошибка!", ex);
        return new ApiErrorResponse("Некорректные параметры запроса", HttpStatus.BAD_REQUEST.toString(), ex);
    }
}
