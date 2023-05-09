package ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler.impl.linkupdatechecker.github;

import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.domain.linkcontent.GithubContent;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;

@Component
public class GithubPushTimeChecker implements GithubLinksUpdateChecker {
    @Override
    public UpdateMessage checkUpdate(GithubContent newContent, GithubContent oldContent) {
        if (newContent.getPushedAt().equals(oldContent.getPushedAt())) {
            return null;
        }

        return new UpdateMessage("- В репозиторий запушили новые изменения;");
    }
}
