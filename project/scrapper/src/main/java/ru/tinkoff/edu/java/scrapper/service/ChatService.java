package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.model.ChatEntity;

import java.util.List;

public interface ChatService {
    void register(long id);
    void unregister(long id);
    List<ChatEntity> findByLink(String url);
}
