package ru.tinkoff.edu.java.scrapper.service.jooq;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.exception.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.domain.ChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class JooqChatService implements ChatService {
    private final JooqChatRepository jooqChatRepository;
    private final JooqSubscriptionRepository jooqSubscriptionRepository;
    private final JooqLinkRepository jooqLinkRepository;

    @Override
    @Transactional
    public void register(long id) {
        jooqChatRepository.add(id);
    }

    @Override
    @Transactional
    public void unregister(long id) {
        int removedRows = jooqChatRepository.removeById(id);
        if (removedRows == 0) {
            throw new ChatNotFoundException(id);
        }

        jooqSubscriptionRepository.removeLinksWithoutSubscribers();
    }

    @Override
    public List<ChatEntity> findByLink(String url) {
        LinkEntity link = jooqLinkRepository.find(url).orElseThrow(() -> new LinkNotFoundException(url));
        return jooqSubscriptionRepository.findChatsByLinkId(link.id());
    }
}
