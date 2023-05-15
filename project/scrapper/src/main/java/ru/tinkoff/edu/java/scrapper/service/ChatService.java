package ru.tinkoff.edu.java.scrapper.service;

import java.util.List;
import ru.tinkoff.edu.java.scrapper.domain.ChatEntity;

public interface ChatService {
    void register(long id);

    void unregister(long id);

    List<ChatEntity> findByLink(String url);
}
