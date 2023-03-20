package ru.tinkoff.edu.java.bot.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;


@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badRequest(Exception ex) {
        return new ApiErrorResponse("Некорректные параметры запроса", HttpStatus.BAD_REQUEST.toString(), ex);
    }
}
