package ru.tinkoff.edu.java.scrapper.exception;

import org.webjars.NotFoundException;

import java.net.URI;

public class LinkNotFoundException extends NotFoundException {
    public LinkNotFoundException(String url) {
        super(String.format("Ссылка (%s) не найдена", url));
    }

    public LinkNotFoundException(Long id) {
        super(String.format("Ссылка с id (%d) не найдена", id));
    }
}
