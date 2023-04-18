package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.model.LinkEntity;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.time.Duration;
import java.util.List;

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
        List<LinkEntity> linksToUpdate = linkService.findLinksUpdatedBefore(linkUpdateInterval);
        for (LinkEntity linkEntity : linksToUpdate) {
            LinkEntity updatedLink = linkUpdater.update(linkEntity);
            linkService.save(updatedLink);
        }
    }
}
