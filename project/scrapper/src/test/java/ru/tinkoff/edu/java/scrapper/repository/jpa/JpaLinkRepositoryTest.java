package ru.tinkoff.edu.java.scrapper.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkEntity;
import ru.tinkoff.edu.java.scrapper.environment.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.domain.ChatEntity;
import ru.tinkoff.edu.java.scrapper.environment.JpaIntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaChatRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JpaLinkRepositoryTest extends JpaIntegrationEnvironment {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JpaLinkRepository linkRepository;

    private static final String TEST_URL = "https://github.com/Corenlix/tinkoff-java-course";

    @Test
    @Transactional
    @Rollback
    void when_addOne_oneAdded() {
        // given
        JpaLinkEntity entity = new JpaLinkEntity();
        entity.setUrl(TEST_URL);

        // when
        List<LinkEntity> allBefore = getAll();
        linkRepository.save(entity);
        linkRepository.flush();
        List<LinkEntity> allAfter = getAll();

        // then
        assertThat(allBefore).hasSize(0);
        assertThat(allAfter).hasSize(1);
    }

    @Test
    @Transactional
    @Rollback
    void when_findAll_nothingExists_nothingReturned() {
        // given

        // when
        List<JpaLinkEntity> all = linkRepository.findAll();

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
        List<JpaLinkEntity> all = linkRepository.findAll();

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
        List<LinkEntity> allBefore = getAll();
        JpaLinkEntity link = linkRepository.findByUrl(TEST_URL).get();
        linkRepository.delete(link);
        linkRepository.flush();
        List<LinkEntity> allAfter = getAll();

        // then
        assertThat(allBefore).hasSize(1);
        assertThat(allAfter).hasSize(0);
    }

    private List<LinkEntity> getAll() {
        return jdbcTemplate.query("select id, url, updated_at, content_json from link", new DataClassRowMapper<>(LinkEntity.class));
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
