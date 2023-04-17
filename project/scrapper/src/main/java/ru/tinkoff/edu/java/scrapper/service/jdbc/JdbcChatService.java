package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.repository.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

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
}