package ru.tinkoff.edu.java.scrapper.repository.jdbc;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.environment.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.domain.ChatEntity;
import ru.tinkoff.edu.java.scrapper.environment.JdbcIntegrationEnvironment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcChatRepositoryTest extends JdbcIntegrationEnvironment {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    void when_addOne_Added() {
        // given
        Long id = 1L;

        // when
        chatRepository.add(id);
        List<ChatEntity> all = findAll();

        // then
        assertThat(all).hasSize(1);
        assertThat(all.get(0).id()).isEqualTo(id);
    }

    @Test
    @Transactional
    @Rollback
    void when_remove_removed() {
        // given
        Long id = 1L;
        create(id);

        // when
        List<ChatEntity> allBeforeRemove = findAll();
        chatRepository.removeById(id);
        List<ChatEntity> all = findAll();

        // then
        assertThat(allBeforeRemove).hasSize(1);
        assertThat(all).hasSize(0);
    }

    @Test
    @Transactional
    @Rollback
    void when_getAll_nothingExists_nothingReturned() {
        // given

        // when
        List<ChatEntity> all = chatRepository.findAll();

        // then
        assertThat(all).hasSize(0);
    }

    @Test
    @Transactional
    @Rollback
    void when_getAll_oneExists_oneReturned() {
        // given
        Long id = 1L;
        create(id);

        // when
        List<ChatEntity> all = chatRepository.findAll();

        // then
        assertThat(all).hasSize(1);
    }

    private List<ChatEntity> findAll() {
        return jdbcTemplate.query("select id from chat", new DataClassRowMapper<>(ChatEntity.class));
    }

    private void create(Long id) {
        jdbcTemplate.update("insert into chat (id) values (?)", id);
    }
}
