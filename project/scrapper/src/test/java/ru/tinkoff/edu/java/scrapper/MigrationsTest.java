package ru.tinkoff.edu.java.scrapper;

import ru.tinkoff.edu.java.scrapper.environment.IntegrationEnvironment;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MigrationsTest extends IntegrationEnvironment {
    private static final String GET_ALL_TABLES_QUERY = """
            select table_name
            from information_schema.tables
            where table_schema='public' and table_catalog = current_database()
            """;

    @SneakyThrows
    @Test
    void when_ContainerRun_TablesCreate() {
        //given
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        //when
        ResultSet result = statement.executeQuery(GET_ALL_TABLES_QUERY);

        //then
        List<String> tablesShouldExist = List.of("link", "chat", "subscription", "databasechangelog", "databasechangeloglock");
        assertThat(getTableNames(result)).containsAll(tablesShouldExist);
    }

    private List<String> getTableNames(ResultSet result) throws SQLException {
        List<String> names = new ArrayList<>();
        while (result.next()) {
            names.add(result.getString("TABLE_NAME"));
        }

        return names;
    }
}