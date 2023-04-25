package ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler.impl.linkupdatechecker.stackoverflow;

import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.domain.linkcontent.StackoverflowContent;

public interface StackOverflowUpdateChecker {
    UpdateMessage checkUpdate(StackoverflowContent newContent, StackoverflowContent oldContent);
}
