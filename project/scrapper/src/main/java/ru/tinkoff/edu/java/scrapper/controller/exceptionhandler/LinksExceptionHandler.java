package ru.tinkoff.edu.java.scrapper.controller.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;

@RestControllerAdvice
public class LinksExceptionHandler {

    @ExceptionHandler(value = {LinkNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse linkNotFound(LinkNotFoundException ex) {
        return new ApiErrorResponse("Ссылка не найдена", HttpStatus.NOT_FOUND.toString(), ex);
    }
}
