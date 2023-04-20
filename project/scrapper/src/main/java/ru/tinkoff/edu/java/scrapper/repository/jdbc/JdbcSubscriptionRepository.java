package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.ChatEntity;
import ru.tinkoff.edu.java.scrapper.model.LinkEntity;
import ru.tinkoff.edu.java.scrapper.model.SubscriptionEntity;
import ru.tinkoff.edu.java.scrapper.exception.SubscriptionNotFoundException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcSubscriptionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<SubscriptionEntity> rowMapper = new DataClassRowMapper<>(SubscriptionEntity.class);
    private final RowMapper<ChatEntity> chatRowMapper = new DataClassRowMapper<>(ChatEntity.class);
    private final RowMapper<LinkEntity> linkRowMapper = new DataClassRowMapper<>(LinkEntity.class);

    private final static String ADD_QUERY = "insert into subscription (chat_id, link_id) values (?, ?)";
    private final static String FIND_ALL_QUERY = "select chat_id, link_id from subscription";
    private final static String REMOVE_QUERY = "delete from subscription where chat_id = ? and link_id = ?";
    private final static String REMOVE_LINKS_WITHOUT_SUBSCRIBERS_QUERY = """
            delete from link\s
            where (select count(link_id) from subscription where link_id = link.id) = 0
            """;
    private final static String FIND_BY_LINK_ID_QUERY = """
            select id 
            from chat
            join subscription on chat.id = chat_id
            where link_id = ?
            """;
    private final static String COUNT_BY_LINK_ID_QUERY = "select count(*) from subscription where link_id = ?";
    private final static String FIND_LINKS_BY_CHAT_ID_QUERY = """
            select id, url, updated_at, content_json
            from link\s
            join subscription s on link.id = s.link_id
            where chat_id = ?
            """;

    public void add(Long chatId, Long linkId) {
        jdbcTemplate.update(ADD_QUERY, chatId, linkId);
    }

    public List<SubscriptionEntity> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, rowMapper);
    }

    public List<ChatEntity> findChatsByLinkId(Long linkId) {
        return jdbcTemplate.query(FIND_BY_LINK_ID_QUERY, chatRowMapper, linkId);
    }

    public List<LinkEntity> findLinksByChatId(Long chatId) {
        return jdbcTemplate.query(FIND_LINKS_BY_CHAT_ID_QUERY, linkRowMapper, chatId);
    }

    public void remove(Long chatId, Long linkId) {
        int removedCount = jdbcTemplate.update(REMOVE_QUERY, chatId, linkId);
        if (removedCount == 0) {
            throw new SubscriptionNotFoundException(chatId, linkId);
        }
    }

    public void removeLinksWithoutSubscribers() {
        jdbcTemplate.update(REMOVE_LINKS_WITHOUT_SUBSCRIBERS_QUERY);
    }

    public Integer countByLinkId(Long linkId) {
        return jdbcTemplate.queryForObject(COUNT_BY_LINK_ID_QUERY, Integer.class, linkId);
    }
}