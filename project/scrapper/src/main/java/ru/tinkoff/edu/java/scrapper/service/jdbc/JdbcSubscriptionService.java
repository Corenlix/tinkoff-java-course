package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.model.ChatEntity;
import ru.tinkoff.edu.java.scrapper.model.LinkEntity;
import ru.tinkoff.edu.java.scrapper.repository.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.SubscriptionService;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JdbcSubscriptionService implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final JdbcLinkRepository linkRepository;
    private final JdbcChatRepository chatRepository;

    @Override
    @Transactional
    public LinkEntity subscribe(Long chatId, URI url) {
        try {
            LinkEntity linkEntity = linkRepository.find(url.toString());
            subscriptionRepository.add(chatId, linkEntity.id());
            return linkEntity;
        } catch (EmptyResultDataAccessException exception) {
            Long linkId = linkRepository.add(url.toString());
            subscriptionRepository.add(chatId, linkId);
            return linkRepository.findById(linkId);
        }
    }

    @Override
    @Transactional
    public LinkEntity unsubscribe(Long chatId, URI url) {
        LinkEntity linkEntity = linkRepository.find(url.toString());
        subscriptionRepository.remove(chatId, linkEntity.id());
        Integer subscriptions = subscriptionRepository.countByLinkId(linkEntity.id());
        if (subscriptions == 0) {
            linkRepository.removeById(linkEntity.id());
        }
        return linkEntity;
    }

    @Override
    public List<LinkEntity> findLinksByChatId(Long chatId) {
        return linkRepository.findByChatId(chatId);
    }

    @Override
    public List<ChatEntity> findChatsByLinkId(Long linkId) {
        return chatRepository.findByLinkId(linkId);
    }
}