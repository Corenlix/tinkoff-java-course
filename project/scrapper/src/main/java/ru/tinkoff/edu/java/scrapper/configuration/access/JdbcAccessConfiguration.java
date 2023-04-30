package ru.tinkoff.edu.java.scrapper.configuration.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcChatService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public LinkService linkService(
            JdbcLinkRepository linkRepository,
            JdbcSubscriptionRepository subscriptionRepository,
            LinkUpdater linkUpdater
    ) {
        return new JdbcLinkService(subscriptionRepository, linkRepository, linkUpdater);
    }

    @Bean
    public ChatService chatService(
            JdbcChatRepository chatRepository,
            JdbcLinkRepository linkRepository,
            JdbcSubscriptionRepository subscriptionRepository
    ) {
        return new JdbcChatService(chatRepository, linkRepository, subscriptionRepository);
    }
}
