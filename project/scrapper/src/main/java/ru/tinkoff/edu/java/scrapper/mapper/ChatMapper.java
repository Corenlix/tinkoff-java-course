package ru.tinkoff.edu.java.scrapper.mapper;

import org.mapstruct.Mapper;
import ru.tinkoff.edu.java.scrapper.domain.ChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    ChatEntity toChat(JpaChatEntity jpaChatEntity);

    List<ChatEntity> toChatsList(List<JpaChatEntity> jpaChatsList);
}
