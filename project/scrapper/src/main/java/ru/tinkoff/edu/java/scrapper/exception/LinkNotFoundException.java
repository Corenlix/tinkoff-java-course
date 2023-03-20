package ru.tinkoff.edu.java.scrapper.exception;

import org.webjars.NotFoundException;

public class LinkNotFoundException extends NotFoundException {
    public LinkNotFoundException(String message) {
        super(message);
    }
}
