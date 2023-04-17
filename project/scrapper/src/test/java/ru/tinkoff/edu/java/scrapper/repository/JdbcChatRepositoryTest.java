package ru.tinkoff.edu.java.scrapper.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.ChatDto;
import ru.tinkoff.edu.java.scrapper.environment.IntegrationEnvironment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class JdbcChatRepositoryTest extends IntegrationEnvironment {
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
        List<ChatDto> all = findAll();

        // then
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getId()).isEqualTo(id);
    }

    @Test
    @Transactional
    @Rollback
    void when_add_alreadyExist_throwsException() {
        // given
        Long id = 1L;

        // when
        chatRepository.add(id);

        // then
        assertThatThrownBy(() -> chatRepository.add(id)).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @Transactional
    @Rollback
    void when_remove_removed() {
        // given
        Long id = 1L;
        create(id);

        // when
        List<ChatDto> allBeforeRemove = findAll();
        chatRepository.removeById(id);
        List<ChatDto> all = findAll();

        // then
        assertThat(allBeforeRemove).hasSize(1);
        assertThat(all).hasSize(0);
    }

    @Test
    @Transactional
    @Rollback
    void when_remove_notExists_nothingRemoved() {
        // given
        Long id = 1L;
        create(id);
        Long notExistId = 2L;

        // when
        chatRepository.removeById(notExistId);
        List<ChatDto> all = findAll();

        // then
        assertThat(all).hasSize(1);
    }

    @Test
    @Transactional
    @Rollback
    void when_getAll_nothingExists_nothingReturned() {
        // given

        // when
        List<ChatDto> all = chatRepository.findAll();

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
        List<ChatDto> all = chatRepository.findAll();

        // then
        assertThat(all).hasSize(1);
    }

    private List<ChatDto> findAll() {
        return jdbcTemplate.query("select id from chat", new BeanPropertyRowMapper<>(ChatDto.class));
    }

    private void create(Long id) {
        jdbcTemplate.update("insert into chat (id) values (?)", id);
    }
}
