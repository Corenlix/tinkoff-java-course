package ru.tinkoff.edu.java.scrapper.exception;

import org.webjars.NotFoundException;

public class ChatNotFoundException extends NotFoundException {
    public ChatNotFoundException(String message) {
        super(message);
    }
}
