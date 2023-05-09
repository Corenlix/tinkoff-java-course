package ru.tinkoff.edu.java.scrapper.configuration.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.mapper.ChatMapper;
import ru.tinkoff.edu.java.scrapper.mapper.LinkMapper;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public LinkService linkService(
            JpaLinkRepository linkRepository,
            JpaChatRepository jpaChatRepository,
            LinkUpdater linkUpdater,
            LinkMapper linkMapper
    ) {
        return new JpaLinkService(linkRepository, jpaChatRepository, linkUpdater, linkMapper);
    }

    @Bean
    public ChatService chatService(
            JpaChatRepository chatRepository,
            JpaLinkRepository linkRepository,
            ChatMapper chatMapper
    ) {
        return new JpaChatService(chatRepository, linkRepository, chatMapper);
    }
}
