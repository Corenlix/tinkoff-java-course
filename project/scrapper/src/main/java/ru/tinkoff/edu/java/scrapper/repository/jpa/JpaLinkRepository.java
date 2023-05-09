package ru.tinkoff.edu.java.scrapper.repository.jpa;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkEntity;

@Repository
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public interface JpaLinkRepository extends JpaRepository<JpaLinkEntity, Long> {
    Optional<JpaLinkEntity> findByUrl(String url);

    @Query(value = "SELECT l FROM JpaLinkEntity l where l.updatedAt < ?1")
    List<JpaLinkEntity> findUpdatedBefore(OffsetDateTime dateTime);

    List<JpaLinkEntity> findByChatsId(Long chatId);
}
