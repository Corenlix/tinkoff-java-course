package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.SubscriptionEntity;
import ru.tinkoff.edu.java.scrapper.exception.SubscriptionNotFoundException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcSubscriptionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<SubscriptionEntity> mapper = new DataClassRowMapper<>(SubscriptionEntity.class);

    private final static String ADD_QUERY = "insert into subscription (chat_id, link_id) values (?, ?)";
    private final static String FIND_ALL_QUERY = "select chat_id, link_id from subscription";
    private final static String REMOVE_QUERY = "delete from subscription where chat_id = ? and link_id = ?";

    public void add(Long chatId, Long linkId) {
        jdbcTemplate.update(ADD_QUERY, chatId, linkId);
    }

    public List<SubscriptionEntity> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, mapper);
    }

    public void remove(Long chatId, Long linkId) {
        int removedCount = jdbcTemplate.update(REMOVE_QUERY, chatId, linkId);
        if (removedCount == 0) {
            throw new SubscriptionNotFoundException(chatId, linkId);
        }
    }
}