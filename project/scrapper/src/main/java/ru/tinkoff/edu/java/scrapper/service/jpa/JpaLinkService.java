package ru.tinkoff.edu.java.scrapper.service.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkEntity;
import ru.tinkoff.edu.java.scrapper.exception.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.mapper.LinkMapper;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private final JpaLinkRepository linkRepository;
    private final JpaChatRepository chatRepository;
    private final LinkMapper linkMapper;

    @Override
    @Transactional
    public LinkEntity add(Long chatId, URI url) {
        JpaLinkEntity linkEntity = new JpaLinkEntity();
        linkEntity.setUrl(url.toString());

        JpaChatEntity chat = chatRepository.findById(chatId).orElseGet(() -> {
            JpaChatEntity chatEntity = new JpaChatEntity();
            chatEntity.setId(chatId);
            chatRepository.save(chatEntity);
            return chatEntity;
        });

        if (!linkEntity.getChats().contains(chat)) {
            linkEntity.getChats().add(chat);
        }
        linkRepository.save(linkEntity);
        return linkMapper.toLink(linkEntity);
    }

    @Override
    @Transactional
    public LinkEntity remove(Long chatId, URI url) {
        JpaLinkEntity linkEntity = linkRepository.findByUrl(url.toString()).orElseThrow(() -> new LinkNotFoundException(url.toString()));
        JpaChatEntity chatEntity = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException(chatId));
        linkEntity.getChats().remove(chatEntity);
        linkRepository.save(linkEntity);

        return linkMapper.toLink(linkEntity);
    }

    @Override
    public List<LinkEntity> findByChatId(Long chatId) {
        JpaChatEntity chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException(chatId));
        return linkMapper.toLinksList(chat.getLinks());
    }

    @Override
    public List<LinkEntity> findLinksUpdatedBefore(OffsetDateTime dateTime) {
        return linkMapper.toLinksList(linkRepository.findUpdatedBefore(dateTime));
    }

    @Override
    @Transactional
    public void save(LinkEntity linkEntity) {
        JpaLinkEntity jpaLinkEntity = linkRepository.findById(linkEntity.id()).orElseThrow(() -> new LinkNotFoundException(linkEntity.id()));
        jpaLinkEntity.setUpdatedAt(linkEntity.updatedAt());
        jpaLinkEntity.setContentJson(linkEntity.contentJson());
        linkRepository.save(jpaLinkEntity);
    }
}
