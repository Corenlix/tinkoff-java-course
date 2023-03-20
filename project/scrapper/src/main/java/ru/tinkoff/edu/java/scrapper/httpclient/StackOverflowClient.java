package ru.tinkoff.edu.java.scrapper.httpclient;

import ru.tinkoff.edu.java.scrapper.dto.StackExchangeQuestionResponse;

public interface StackOverflowClient {
    StackExchangeQuestionResponse fetchQuestion(Long id);
}
