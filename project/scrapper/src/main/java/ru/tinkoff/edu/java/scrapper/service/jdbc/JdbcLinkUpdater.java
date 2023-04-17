package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;
import ru.tinkoff.edu.java.scrapper.repository.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JdbcLinkUpdater implements LinkUpdater {
    private final JdbcLinkRepository jdbcLinkRepository;
    private final Duration interval = Duration.ofHours(1);

    @Override
    @Transactional
    public int update() {
        List<LinkDto> linksUpdatedBefore = jdbcLinkRepository.findLinksUpdatedBefore(interval);
        return 0;
    }

    private void updateLink() {

    }
}
