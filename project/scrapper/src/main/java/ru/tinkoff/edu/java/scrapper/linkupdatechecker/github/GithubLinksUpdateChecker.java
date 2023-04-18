package ru.tinkoff.edu.java.scrapper.linkupdatechecker.github;

import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.model.linkcontent.GithubContent;

public interface GithubLinksUpdateChecker {
    UpdateMessage checkUpdate(GithubContent newContent, GithubContent oldContent);
}
