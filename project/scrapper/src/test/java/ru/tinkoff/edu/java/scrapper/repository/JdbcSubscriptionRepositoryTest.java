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
import ru.tinkoff.edu.java.scrapper.dto.SubscriptionDto;
import ru.tinkoff.edu.java.scrapper.environment.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

import java.sql.PreparedStatement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@SpringBootTest
public class JdbcSubscriptionRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    private static final String TEST_URL = "https://github.com/Corenlix/tinkoff-java-course";

    @Test
    @Transactional
    @Rollback
    void when_addOne_oneAdded() {
        // given
        Long chatId = 1L;
        Long linkId = createLink(TEST_URL);
        createChat(chatId);

        // when
        List<SubscriptionDto> allBefore = getAll();
        subscriptionRepository.add(chatId, linkId);
        List<SubscriptionDto> allAfter = getAll();

        // then
        assertThat(allBefore).hasSize(0);
        assertThat(allAfter).hasSize(1);
    }

    @Test
    @Transactional
    @Rollback
    void when_add_alreadyExist_throwsException() {
        // given
        Long chatId = 1L;
        Long linkId = createLink(TEST_URL);
        createChat(chatId);

        // when
        subscriptionRepository.add(chatId, linkId);

        // then
        assertThatThrownBy(() -> subscriptionRepository.add(chatId, linkId)).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @Transactional
    @Rollback
    void when_findAll_nothingExists_nothingReturned() {
        // given

        // when
        List<SubscriptionDto> all = subscriptionRepository.findAll();

        // then
        assertThat(all).hasSize(0);
    }

    @Test
    @Transactional
    @Rollback
    void when_findAll_oneExists_oneReturned() {
        // given
        Long chatId = 1L;
        Long linkId = createLink(TEST_URL);
        createChat(chatId);
        createSubscription(chatId, linkId);

        // when
        List<SubscriptionDto> all = subscriptionRepository.findAll();

        // then
        assertThat(all).hasSize(1);
    }

    @Test
    @Transactional
    @Rollback
    void when_remove_oneExists_oneRemoved() {
        // given
        Long chatId = 1L;
        Long linkId = createLink(TEST_URL);
        createChat(chatId);
        createSubscription(chatId, linkId);

        // when
        List<SubscriptionDto> allBefore = getAll();
        subscriptionRepository.remove(chatId, linkId);
        List<SubscriptionDto> allAfter = getAll();

        // then
        assertThat(allBefore).hasSize(1);
        assertThat(allAfter).hasSize(0);
    }

    @Test
    @Transactional
    @Rollback
    void when_remove_chat_subscriptionRemoved() {
        // given
        Long chatId = 1L;
        Long linkId = createLink(TEST_URL);
        createChat(chatId);
        createSubscription(chatId, linkId);

        // when
        List<SubscriptionDto> allBefore = getAll();
        removeChat(chatId);
        List<SubscriptionDto> allAfter = getAll();

        // then
        assertThat(allBefore).hasSize(1);
        assertThat(allAfter).hasSize(0);
    }

    @Test
    @Transactional
    @Rollback
    void then_remove_notExists_nothingRemoved() {
        // given
        Long chatId = 1L;
        Long linkId = 1L;
        createChat(2L);
        Long createdLinkId = createLink(TEST_URL);
        createSubscription(2L, createdLinkId);

        // when
        List<SubscriptionDto> allBefore = getAll();
        subscriptionRepository.remove(chatId, linkId);
        List<SubscriptionDto> allAfter = getAll();

        // then
        assertThat(allBefore).hasSize(1);
        assertThat(allAfter).hasSize(1);
    }

    private List<SubscriptionDto> getAll() {
        return jdbcTemplate.query("select chat_id, link_id from subscription", new BeanPropertyRowMapper<>(SubscriptionDto.class));
    }

    private Long createLink(String url) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into link (url) values (?)", new String[]{"id"});
            ps.setString(1, url);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void createChat(Long id) {
        jdbcTemplate.update("insert into chat (id) values (?)", id);
    }

    private void removeChat(Long id) {
        jdbcTemplate.update("delete from chat where id = ?", id);
    }

    private void createSubscription(Long chatId, Long linkId) {
        jdbcTemplate.update("insert into subscription (chat_id, link_id) values (?, ?)", chatId, linkId);
    }
}
