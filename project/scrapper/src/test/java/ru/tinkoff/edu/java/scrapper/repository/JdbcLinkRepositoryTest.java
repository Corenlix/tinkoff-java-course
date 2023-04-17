package ru.tinkoff.edu.java.scrapper.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;
import ru.tinkoff.edu.java.scrapper.environment.IntegrationEnvironment;

import java.sql.PreparedStatement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@SpringBootTest
public class JdbcLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcLinkRepository linkRepository;

    private static final String TEST_URL = "https://github.com/Corenlix/tinkoff-java-course";

    @Test
    @Transactional
    @Rollback
    void when_addOne_oneAdded() {
        // given

        // when
        List<LinkDto> allBefore = getAll();
        linkRepository.add(TEST_URL);
        List<LinkDto> allAfter = getAll();

        // then
        assertThat(allBefore).hasSize(0);
        assertThat(allAfter).hasSize(1);
    }

    @Test
    @Transactional
    @Rollback
    void when_add_alreadyExist_throwsException() {
        // given

        // when
        linkRepository.add(TEST_URL);

        // then
        assertThatThrownBy(() -> linkRepository.add(TEST_URL)).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @Transactional
    @Rollback
    void when_findAll_nothingExists_nothingReturned() {
        // given

        // when
        List<LinkDto> all = linkRepository.findAll();

        // then
        assertThat(all).hasSize(0);
    }

    @Test
    @Transactional
    @Rollback
    void when_findAll_oneExists_oneReturned() {
        // given
        addLink(TEST_URL);

        // when
        List<LinkDto> all = linkRepository.findAll();

        // then
        assertThat(all).hasSize(1);
    }

    @Test
    @Transactional
    @Rollback
    void when_remove_oneExists_oneRemoved() {
        // given
        addLink(TEST_URL);

        // when
        List<LinkDto> allBefore = getAll();
        linkRepository.remove(TEST_URL);
        List<LinkDto> allAfter = getAll();

        // then
        assertThat(allBefore).hasSize(1);
        assertThat(allAfter).hasSize(0);
    }

    @Test
    @Transactional
    @Rollback
    void when_remove_notExists_nothingRemoved() {
        // given

        // when
        List<LinkDto> allBefore = getAll();
        linkRepository.remove(TEST_URL);
        List<LinkDto> allAfter = getAll();

        // then
        assertThat(allBefore).hasSize(0);
        assertThat(allAfter).hasSize(0);
    }

    private List<LinkDto> getAll() {
        return jdbcTemplate.query("select id, url from link", new BeanPropertyRowMapper<>(LinkDto.class));
    }

    private Long addLink(String url) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into link (url) values (?)", new String[]{"id"});
            ps.setString(1, url);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}