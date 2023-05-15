package ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler.impl.linkupdatechecker.github;

import ru.tinkoff.edu.java.scrapper.domain.linkcontent.GithubContent;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;

public interface GithubLinksUpdateChecker {
    UpdateMessage checkUpdate(GithubContent newContent, GithubContent oldContent);
}
