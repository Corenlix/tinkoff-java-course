package ru.tinkoff.edu.java.scrapper.exception;

import org.webjars.NotFoundException;

import java.net.URI;

public class LinkNotFoundException extends NotFoundException {
    public LinkNotFoundException(URI link) {
        super(String.format("Ссылка %s не найдена", link.toString()));
    }
}
