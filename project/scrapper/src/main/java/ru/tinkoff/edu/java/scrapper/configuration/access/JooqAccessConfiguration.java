package ru.tinkoff.edu.java.scrapper.configuration.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqChatService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    public LinkService linkService(
            JooqLinkRepository linkRepository,
            JooqSubscriptionRepository subscriptionRepository,
            LinkUpdater linkUpdater
    ) {
        return new JooqLinkService(subscriptionRepository, linkRepository, linkUpdater);
    }

    @Bean
    public ChatService chatService(
            JooqChatRepository chatRepository,
            JooqLinkRepository linkRepository,
            JooqSubscriptionRepository subscriptionRepository
    ) {
        return new JooqChatService(chatRepository, subscriptionRepository, linkRepository);
    }
}
