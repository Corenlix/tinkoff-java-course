package ru.tinkoff.edu.java.scrapper.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatEntity;

import java.util.List;

@Repository
public interface JpaChatRepository extends JpaRepository<JpaChatEntity, Long> {
    List<JpaChatEntity> findByLinksId(Long linkId);
}
