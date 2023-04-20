package ru.tinkoff.edu.java.scrapper.repository.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat;
import ru.tinkoff.edu.java.scrapper.exception.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.ChatEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JooqChatRepository {
    private final DSLContext context;
    private final Chat chat = Chat.CHAT;

    public void add(Long id) {
        context.insertInto(chat)
                .set(chat.ID, id)
                .execute();
    }

    public void removeById(Long id) {
        int removedCount = context.delete(chat)
                .where(chat.ID.eq(id))
                .execute();
        if (removedCount == 0)
            throw new ChatNotFoundException(id);
    }

    public List<ChatEntity> findAll() {
        return context.select(chat.fields())
                .from(chat)
                .fetchInto(ChatEntity.class);
    }
}
