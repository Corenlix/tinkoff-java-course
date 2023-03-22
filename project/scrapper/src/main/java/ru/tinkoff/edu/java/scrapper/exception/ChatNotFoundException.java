package ru.tinkoff.edu.java.scrapper.exception;

import org.webjars.NotFoundException;

public class ChatNotFoundException extends NotFoundException {
    public ChatNotFoundException(Long id) {
        super(String.format("Чат с (Id: %d) не найден"));
    }
}
