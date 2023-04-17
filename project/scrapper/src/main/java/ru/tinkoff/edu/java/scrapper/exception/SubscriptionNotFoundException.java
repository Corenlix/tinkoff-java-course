package ru.tinkoff.edu.java.scrapper.exception;

import org.webjars.NotFoundException;

public class SubscriptionNotFoundException extends NotFoundException {

    public SubscriptionNotFoundException(Long chatId, Long linkId) {
        super(String.format("Подписка с ChatId:%d и LinkId:%d не найдена!", chatId, linkId));
    }
}
