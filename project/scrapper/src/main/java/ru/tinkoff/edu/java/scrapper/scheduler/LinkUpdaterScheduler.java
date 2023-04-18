package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.model.LinkEntity;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.UpdateMessagesSender;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final Duration interval = Duration.ofHours(1);
    private final LinkUpdater linkUpdater;
    private final LinkService linkService;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        List<LinkEntity> linksToUpdate = linkService.findLinksUpdatedBefore(interval);
        for (LinkEntity linkEntity : linksToUpdate) {
            LinkEntity updatedLink = linkUpdater.update(linkEntity);
            linkService.save(updatedLink);
        }
    }
}
