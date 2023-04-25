package ru.tinkoff.edu.java.scrapper.service.jooq;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final JooqSubscriptionRepository subscriptionRepository;
    private final JooqLinkRepository linkRepository;
    private final LinkUpdater linkUpdater;

    @Override
    @Transactional
    public LinkEntity add(Long chatId, URI url) {
        LinkEntity link = linkRepository.find(url.toString())
                .orElseGet(() -> linkRepository.add(url.toString()));

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
