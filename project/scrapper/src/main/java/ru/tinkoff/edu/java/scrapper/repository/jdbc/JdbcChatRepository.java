package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.ChatEntity;
import ru.tinkoff.edu.java.scrapper.exception.ChatNotFoundException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcChatRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ChatEntity> rowMapper = new DataClassRowMapper<>(ChatEntity.class);

    private final static String ADD_QUERY = "insert into chat (id) values (?)";
    private final static String REMOVE_BY_ID_QUERY = "delete from chat where id = ?";
    private final static String FIND_ALL_QUERY = "select id from chat";

    public List<ChatEntity> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, rowMapper);
    }

    public void add(Long id) {
        jdbcTemplate.update(ADD_QUERY, id);
    }

    public void removeById(Long id) {
        int removedCount = jdbcTemplate.update(REMOVE_BY_ID_QUERY, id);
        if (removedCount == 0) {
            throw new ChatNotFoundException(id);
        }
    }
}
