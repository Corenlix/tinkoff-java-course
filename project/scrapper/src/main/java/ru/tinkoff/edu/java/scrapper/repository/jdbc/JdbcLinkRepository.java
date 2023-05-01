package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;

import java.sql.PreparedStatement;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcLinkRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<LinkEntity> rowMapper = new DataClassRowMapper<>(LinkEntity.class);

    private final static String ADD_QUERY = "insert into link (url) values (?)";
    private final static String SAVE_QUERY = "update link set url=?, content_json=?::json, updated_at=? where id=?";
    private final static String REMOVE_QUERY = "delete from link where url = ?";
    private final static String FIND_ALL_QUERY = "select id, url, updated_at, content_json from link";
    private final static String FIND_UPDATED_BEFORE_QUERY = "SELECT id, url, updated_at, content_json FROM link WHERE updated_at < ?";
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
    private final static String REMOVE_BY_ID_QUERY = "delete from link where id = ?";


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

    public int save(LinkEntity link) {
        return jdbcTemplate.update(SAVE_QUERY, link.url(), link.contentJson(), link.updatedAt(), link.id());
    }

    public int remove(String url) {
        return jdbcTemplate.update(REMOVE_QUERY, url);
    }

    public Optional<LinkEntity> find(String url) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_QUERY, rowMapper, url));
    }

    public Optional<LinkEntity> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, rowMapper, id));
    }

    public int removeById(Long id) {
        return jdbcTemplate.update(REMOVE_BY_ID_QUERY, id);
    }

    public List<LinkEntity> findLinksUpdatedBefore(OffsetDateTime dateTime) {
        return jdbcTemplate.query(FIND_UPDATED_BEFORE_QUERY, rowMapper, dateTime);
    }
}