package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.ChatDto;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;
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
    public LinkDto subscribe(Long chatId, URI url) {
        try {
            LinkDto linkDto = linkRepository.find(url.toString());
            subscriptionRepository.add(chatId, linkDto.id());
            return linkDto;
        } catch (EmptyResultDataAccessException exception) {
            Long linkId = linkRepository.add(url.toString());
            subscriptionRepository.add(chatId, linkId);
            return linkRepository.findById(linkId);
        }
    }

    @Override
    @Transactional
    public LinkDto unsubscribe(Long chatId, URI url) {
        LinkDto linkDto = linkRepository.find(url.toString());
        subscriptionRepository.remove(chatId, linkDto.id());
        Integer subscriptions = subscriptionRepository.countByLinkId(linkDto.id());
        if (subscriptions == 0) {
            linkRepository.removeById(linkDto.id());
        }
        return linkDto;
    }

    @Override
    public List<LinkDto> findLinksByChatId(Long chatId) {
        return linkRepository.findByChatId(chatId);
    }

    @Override
    public List<ChatDto> findChatsByLinkId(Long linkId) {
        return chatRepository.findByLinkId(linkId);
    }
}