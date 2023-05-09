package ru.tinkoff.edu.java.scrapper.repository.jooq;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import ru.tinkoff.edu.java.scrapper.domain.ChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat;

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
