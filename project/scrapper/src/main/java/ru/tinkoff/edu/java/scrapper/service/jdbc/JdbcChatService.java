package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.model.ChatEntity;
import ru.tinkoff.edu.java.scrapper.model.LinkEntity;
import ru.tinkoff.edu.java.scrapper.repository.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JdbcChatService implements ChatService {
    private final JdbcChatRepository jdbcChatRepository;
    private final JdbcLinkRepository jdbcLinkRepository;

    @Override
    @Transactional
    public void register(long id) {
        jdbcChatRepository.add(id);
    }

    @Override
    @Transactional
    public void unregister(long id) {
        jdbcChatRepository.removeById(id);
        jdbcLinkRepository.removeWithoutSubscribers();
    }

    @Override
    public List<ChatEntity> findByLink(String url) {
        LinkEntity link = jdbcLinkRepository.find(url);
        return jdbcChatRepository.findByLinkId(link.id());
    }
}