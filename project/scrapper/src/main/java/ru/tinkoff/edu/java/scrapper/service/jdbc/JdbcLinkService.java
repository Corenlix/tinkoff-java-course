package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.model.LinkEntity;
import ru.tinkoff.edu.java.scrapper.repository.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdaterImpl;
import ru.tinkoff.edu.java.scrapper.service.UpdateMessagesSender;

import java.net.URI;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JdbcLinkService implements LinkService {
    private final SubscriptionRepository subscriptionRepository;
    private final JdbcLinkRepository linkRepository;
    private final JdbcChatRepository chatRepository;
    private final LinkUpdaterImpl linkUpdater;

    @Override
    @Transactional
    public LinkEntity add(Long chatId, URI url) {
        LinkEntity linkEntity;
        try {
            linkEntity = linkRepository.find(url.toString());
        } catch (EmptyResultDataAccessException exception) {
            Long linkId = linkRepository.add(url.toString());
            subscriptionRepository.add(chatId, linkId);
            linkEntity = linkRepository.findById(linkId);
        }

        LinkEntity updatedLink = linkUpdater.update(linkEntity);
        linkRepository.save(updatedLink);
        subscriptionRepository.add(chatId, linkEntity.id());

        return linkEntity;
    }

    @Override
    @Transactional
    public LinkEntity remove(Long chatId, URI url) {
        LinkEntity linkEntity = linkRepository.find(url.toString());
        subscriptionRepository.remove(chatId, linkEntity.id());
        Integer subscriptions = chatRepository.countByLinkId(linkEntity.id());
        if (subscriptions == 0) {
            linkRepository.removeById(linkEntity.id());
        }

        return linkEntity;
    }

    @Override
    @Transactional
    public void save(LinkEntity linkEntity) {
        linkRepository.save(linkEntity);
    }

    @Override
    public List<LinkEntity> findByChatId(Long chatId) {
        return linkRepository.findByChatId(chatId);
    }

    @Override
    public List<LinkEntity> findLinksUpdatedBefore(Duration interval) {
        return linkRepository.findLinksUpdatedBefore(interval);
    }
}