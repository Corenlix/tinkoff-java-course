package ru.tinkoff.edu.java.scrapper.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exception.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;


@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(value = {LinkNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse linkNotFound(LinkNotFoundException ex) {
        return new ApiErrorResponse("Ссылка не найдена", HttpStatus.NOT_FOUND.toString(), ex);
    }

    @ExceptionHandler(value = {ChatNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse linkNotFound(ChatNotFoundException ex) {
        return new ApiErrorResponse("Чат не существует", HttpStatus.NOT_FOUND.toString(), ex);
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badRequest(Exception ex) {
        return new ApiErrorResponse("Некорректные параметры запроса", HttpStatus.BAD_REQUEST.toString(), ex);
    }
}