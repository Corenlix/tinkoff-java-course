package ru.tinkoff.edu.java.scrapper.repository.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat;
import ru.tinkoff.edu.java.scrapper.domain.ChatEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JooqChatRepository {
    private final DSLContext context;
    private final Chat chat = Chat.CHAT;

    public int add(Long id) {
        return context.insertInto(chat)
                .set(chat.ID, id)
                .execute();
    }

    public int removeById(Long id) {
        return context.delete(chat)
                .where(chat.ID.eq(id))
                .execute();
    }

    public List<ChatEntity> findAll() {
        return context.select(chat.fields())
                .from(chat)
                .fetchInto(ChatEntity.class);
    }
}
