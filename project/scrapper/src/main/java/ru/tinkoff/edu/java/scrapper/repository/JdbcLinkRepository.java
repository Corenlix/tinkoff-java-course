package ru.tinkoff.edu.java.scrapper.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.exception.SubscriptionNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.LinkEntity;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<LinkEntity> rowMapper = new DataClassRowMapper<>(LinkEntity.class);

    private final static String ADD_QUERY = "insert into link (url) values (?)";
    private final static String SAVE_QUERY = "update link set url=?, content_json=?, updated_at=? where id=?";
    private final static String REMOVE_QUERY = "delete from link where url = ?";
    private final static String FIND_ALL_QUERY = "select id, url from link";
    private final static String FIND_UPDATED_BEFORE_QUERY = "SELECT id FROM link WHERE updated_at < ?";
    private final static String FIND_QUERY = """
            select id, url, updated_at, content_json
            from link\s
            where url = ?
            """;
    private final static String FIND_BY_ID_QUERY = """
            select id, url, updated_at, content_json
            from link\s
            where id = ?
            """;
    private final static String FIND_BY_CHAT_ID_QUERY = """
            select id, url, updated_at, content_json
            from link\s
            join subscription s on link.id = s.link_id
            where chat_id = ?
            """;
    private final static String REMOVE_BY_ID_QUERY = "delete from link where id = ?";
    private final static String REMOVE_WITHOUT_SUBSCRIBERS_QUERY = """
            delete from link\s
            where (select count(link_id) from subscription where link_id = link.id) = 0
            """;


    public List<LinkEntity> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, rowMapper);
    }

    public Long add(String url) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(ADD_QUERY, new String[]{"id"});
            ps.setString(1, url);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void save(LinkEntity link) {
        jdbcTemplate.update(SAVE_QUERY, link.url(), link.contentJson(), link.id(), link.updatedAt());
    }

    public void remove(String url) {
        int removedCount = jdbcTemplate.update(REMOVE_QUERY, url);
        if(removedCount == 0) {
            throw new LinkNotFoundException(url);
        }
    }

    public LinkEntity find(String url) {
        return jdbcTemplate.queryForObject(FIND_QUERY, rowMapper, url);
    }

    public LinkEntity findById(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, rowMapper, id);
    }

    public List<LinkEntity> findByChatId(Long chatId) {
        return jdbcTemplate.query(FIND_BY_CHAT_ID_QUERY, rowMapper, chatId);
    }

    public void removeById(Long id) {
        int removedCount = jdbcTemplate.update(REMOVE_BY_ID_QUERY, id);
        if (removedCount == 0) {
            throw new LinkNotFoundException(id);
        }
    }

    public List<LinkEntity> findLinksUpdatedBefore(Duration interval) {
        Instant now = Instant.now();
        Instant threshold = now.minus(interval);
        Timestamp thresholdTimestamp = Timestamp.from(threshold);

        return jdbcTemplate.query(FIND_UPDATED_BEFORE_QUERY, rowMapper, thresholdTimestamp);
    }

    public void removeWithoutSubscribers() {
        jdbcTemplate.update(REMOVE_WITHOUT_SUBSCRIBERS_QUERY);
    }
}