package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.exception.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.domain.ChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;
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
        int removedRows = jdbcChatRepository.removeById(id);
        if (removedRows == 0) {
            throw new ChatNotFoundException(id);
        }

        jdbcSubscriptionRepository.removeLinksWithoutSubscribers();
    }

    @Override
    public List<ChatEntity> findByLink(String url) {
        LinkEntity link = jdbcLinkRepository.find(url).orElseThrow(() -> new LinkNotFoundException(url));
        return jdbcSubscriptionRepository.findChatsByLinkId(link.id());
    }
}