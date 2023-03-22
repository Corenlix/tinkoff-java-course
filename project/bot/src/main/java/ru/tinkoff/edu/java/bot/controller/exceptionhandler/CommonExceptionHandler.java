package ru.tinkoff.edu.java.bot.controller.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;


@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(value = {
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMediaTypeNotSupportedException.class,
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badRequest(Exception ex) {
        return new ApiErrorResponse("Некорректные параметры запроса", HttpStatus.BAD_REQUEST.toString(), ex);
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse serverError(Exception ex) {
        return new ApiErrorResponse("Некорректные параметры запроса", HttpStatus.BAD_REQUEST.toString(), ex);
    }
}
