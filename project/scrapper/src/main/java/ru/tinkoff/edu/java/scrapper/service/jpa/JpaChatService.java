package ru.tinkoff.edu.java.scrapper.service.jpa;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.ChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkEntity;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.mapper.ChatMapper;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {

    private final JpaChatRepository chatRepository;
    private final JpaLinkRepository linkRepository;
    private final ChatMapper chatMapper;

    @Override
    @Transactional
    public void register(long id) {
        JpaChatEntity chat = new JpaChatEntity();
        chat.setId(id);
        chatRepository.save(chat);
    }

    @Override
    @Transactional
    public void unregister(long id) {
        JpaChatEntity chat = new JpaChatEntity();
        chat.setId(id);
        chatRepository.delete(chat);
    }

    @Override
    public List<ChatEntity> findByLink(String url) {
        JpaLinkEntity link = linkRepository.findByUrl(url).orElseThrow(() -> new LinkNotFoundException(url));
        List<JpaChatEntity> chats = chatRepository.findByLinksId(link.getId());
        return chatMapper.toChatsList(chats.stream().toList());
    }
}
