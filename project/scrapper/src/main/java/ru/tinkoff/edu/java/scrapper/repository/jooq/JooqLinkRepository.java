package ru.tinkoff.edu.java.scrapper.repository.jooq;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.JSON;
import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link;

@RequiredArgsConstructor
public class JooqLinkRepository {
    private final DSLContext context;
    private final Link link = Link.LINK;

    public List<LinkEntity> findAll() {
        return context.select(link.fields())
                .from(link)
                .fetchInto(LinkEntity.class);
    }

    public LinkEntity add(String url) {
        return context.insertInto(link)
                .set(link.URL, url)
                .returning(link.fields())
                .fetchOneInto(LinkEntity.class);
    }

    public int save(LinkEntity linkEntity) {
        return context.update(link)
                .set(link.CONTENT_JSON, JSON.valueOf(linkEntity.contentJson()))
                .set(link.UPDATED_AT, linkEntity.updatedAt())
                .where(link.ID.eq(linkEntity.id()))
                .execute();
    }

    public Optional<LinkEntity> find(String url) {
        return Optional.ofNullable(
                context.select(link.fields())
                        .from(link)
                        .where(link.URL.eq(url))
                        .fetchOneInto(LinkEntity.class));
    }

    public Optional<LinkEntity> findById(Long id) {
        return Optional.ofNullable(
                context.select(link.fields())
                        .from(link)
                        .where(link.ID.eq(id))
                        .fetchOneInto(LinkEntity.class));
    }


    public int remove(String url) {
        return context.delete(link)
                .where(link.URL.eq(url))
                .execute();
    }

    public int removeById(Long id) {
        return context.delete(link)
                .where(link.ID.eq(id))
                .execute();
    }

    public List<LinkEntity> findLinksUpdatedBefore(OffsetDateTime dateTime) {
        return context.select(link.fields())
                .from(link)
                .where(link.UPDATED_AT.lessOrEqual(dateTime))
                .fetchInto(LinkEntity.class);
    }
}
