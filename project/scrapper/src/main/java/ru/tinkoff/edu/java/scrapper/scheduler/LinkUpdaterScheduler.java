package ru.tinkoff.edu.java.scrapper.scheduler;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

@Slf4j
@Component
public class LinkUpdaterScheduler {
    private final Duration linkUpdateInterval;
    private final LinkUpdater linkUpdater;
    private final LinkService linkService;

    public LinkUpdaterScheduler(ApplicationConfig applicationConfig, LinkUpdater linkUpdater, LinkService linkService) {
        linkUpdateInterval = applicationConfig.linkUpdateInterval();
        this.linkUpdater = linkUpdater;
        this.linkService = linkService;
    }

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        OffsetDateTime dateTime = OffsetDateTime.now().minus(linkUpdateInterval);
        List<LinkEntity> linksToUpdate = linkService.findLinksUpdatedBefore(dateTime);
        for (LinkEntity linkEntity : linksToUpdate) {
            LinkEntity updatedLink = linkUpdater.update(linkEntity);
            linkService.save(updatedLink);
        }
    }
}
