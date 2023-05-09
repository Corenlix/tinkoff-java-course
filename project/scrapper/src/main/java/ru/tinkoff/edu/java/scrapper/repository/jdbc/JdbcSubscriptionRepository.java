package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.domain.ChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;
import ru.tinkoff.edu.java.scrapper.domain.SubscriptionEntity;

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

    public int add(Long chatId, Long linkId) {
        return jdbcTemplate.update(ADD_QUERY, chatId, linkId);
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

    public int remove(Long chatId, Long linkId) {
        return jdbcTemplate.update(REMOVE_QUERY, chatId, linkId);
    }

    public int removeLinksWithoutSubscribers() {
        return jdbcTemplate.update(REMOVE_LINKS_WITHOUT_SUBSCRIBERS_QUERY);
    }

    public int countByLinkId(Long linkId) {
        return jdbcTemplate.queryForObject(COUNT_BY_LINK_ID_QUERY, Integer.class, linkId);
    }
}
