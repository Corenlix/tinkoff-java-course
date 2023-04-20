package ru.tinkoff.edu.java.scrapper.repository.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.JSON;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Subscription;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.LinkEntity;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

import static org.jooq.impl.DSL.select;

@Repository
@RequiredArgsConstructor
public class JooqLinkRepository {
    private final DSLContext context;
    private final Link link = Link.LINK;

    public List<LinkEntity> findAll() {
        return context.select(link.fields())
                .from(link)
                .fetchInto(LinkEntity.class);
    }

    public Long add(String url) {
        LinkEntity linkEntity = context.insertInto(link)
                .set(link.URL, url)
                .returning(link.fields())
                .fetchOneInto(LinkEntity.class);

        return linkEntity.id();
    }

    public void save(LinkEntity linkEntity) {
        context.update(link)
                .set(link.CONTENT_JSON, JSON.valueOf(linkEntity.contentJson()))
                .set(link.UPDATED_AT, linkEntity.updatedAt())
                .where(link.ID.eq(linkEntity.id()))
                .execute();
    }

    public LinkEntity find(String url) {
        LinkEntity entity = context.select(link.fields())
                .from(link)
                .where(link.URL.eq(url))
                .fetchOneInto(LinkEntity.class);

        if (entity == null)
            throw new LinkNotFoundException(url);

        return entity;
    }

    public LinkEntity findById(Long id) {
        LinkEntity entity = context.select(link.fields())
                .from(link)
                .where(link.ID.eq(id))
                .fetchOneInto(LinkEntity.class);

        if (entity == null)
            throw new LinkNotFoundException(id);

        return entity;
    }


    public void remove(String url) {
        int removedCount = context.delete(link)
                .where(link.URL.eq(url))
                .execute();

        if (removedCount == 0)
            throw new LinkNotFoundException(url);
    }

    public void removeById(Long id) {
        int removedCount = context.delete(link)
                .where(link.ID.eq(id))
                .execute();

        if (removedCount == 0)
            throw new LinkNotFoundException(id);
    }

    public List<LinkEntity> findLinksUpdatedBefore(OffsetDateTime dateTime) {
        return context.select(link.fields())
                .from(link)
                .where(link.UPDATED_AT.lessOrEqual(dateTime))
                .fetchInto(LinkEntity.class);
    }
}