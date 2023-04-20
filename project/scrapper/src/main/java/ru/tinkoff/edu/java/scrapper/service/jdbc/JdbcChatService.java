package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.model.ChatEntity;
import ru.tinkoff.edu.java.scrapper.model.LinkEntity;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcChatService implements ChatService {
    private final JdbcChatRepository jdbcChatRepository;
    private final JdbcLinkRepository jdbcLinkRepository;
    private final JdbcSubscriptionRepository jdbcSubscriptionRepository;

    @Override
    @Transactional
    public void register(long id) {
        jdbcChatRepository.add(id);
    }

    @Override
    @Transactional
    public void unregister(long id) {
        jdbcChatRepository.removeById(id);
        jdbcSubscriptionRepository.removeLinksWithoutSubscribers();
    }

    @Override
    public List<ChatEntity> findByLink(String url) {
        LinkEntity link = jdbcLinkRepository.find(url);
        return jdbcSubscriptionRepository.findChatsByLinkId(link.id());
    }
}