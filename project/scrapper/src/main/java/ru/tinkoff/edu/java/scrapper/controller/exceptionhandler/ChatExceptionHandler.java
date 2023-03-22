package ru.tinkoff.edu.java.scrapper.controller.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exception.ChatNotFoundException;

@RestControllerAdvice
public class ChatExceptionHandler {

    @ExceptionHandler(value = {ChatNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse linkNotFound(ChatNotFoundException ex) {
        return new ApiErrorResponse("Чат не существует", HttpStatus.NOT_FOUND.toString(), ex);
    }
}
