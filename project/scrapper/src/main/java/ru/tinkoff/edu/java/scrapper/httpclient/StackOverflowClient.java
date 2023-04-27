package ru.tinkoff.edu.java.scrapper.httpclient;

import ru.tinkoff.edu.java.scrapper.dto.client.stackexchange.StackExchangeQuestionResponse;

public interface StackOverflowClient {
    StackExchangeQuestionResponse fetchQuestion(Long id);
}
