package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcSubscriptionRepository subscriptionRepository;
    private final JdbcLinkRepository linkRepository;
    private final LinkUpdater linkUpdater;

    @Override
    @Transactional
    public LinkEntity add(Long chatId, URI url) {
        LinkEntity link = linkRepository.find(url.toString()).orElseGet(() -> {
            Long linkId = linkRepository.add(url.toString());
            return linkRepository.findById(linkId).orElseThrow();
        });

        LinkEntity updatedLink = linkUpdater.update(link);
        linkRepository.save(updatedLink);
        subscriptionRepository.add(chatId, updatedLink.id());

        return updatedLink;
    }

    @Override
    @Transactional
    public LinkEntity remove(Long chatId, URI url) {
        LinkEntity link = linkRepository.find(url.toString())
                .orElseThrow(() -> new LinkNotFoundException(url.toString()));

        subscriptionRepository.remove(chatId, link.id());
        if (subscriptionRepository.countByLinkId(link.id()) == 0) {
            linkRepository.removeById(link.id());
        }

        return link;
    }

    @Override
    @Transactional
    public void save(LinkEntity linkEntity) {
        linkRepository.save(linkEntity);
    }

    @Override
    public List<LinkEntity> findByChatId(Long chatId) {
        return subscriptionRepository.findLinksByChatId(chatId);
    }

    @Override
    public List<LinkEntity> findLinksUpdatedBefore(OffsetDateTime dateTime) {
        return linkRepository.findLinksUpdatedBefore(dateTime);
    }
}