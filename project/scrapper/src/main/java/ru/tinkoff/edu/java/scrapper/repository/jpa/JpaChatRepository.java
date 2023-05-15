package ru.tinkoff.edu.java.scrapper.repository.jpa;

import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatEntity;

@Repository
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public interface JpaChatRepository extends JpaRepository<JpaChatEntity, Long> {
    List<JpaChatEntity> findByLinksId(Long linkId);
}
