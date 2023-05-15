package ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler.impl.linkupdatechecker.stackoverflow;

import ru.tinkoff.edu.java.scrapper.domain.linkcontent.StackoverflowContent;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;

public interface StackOverflowUpdateChecker {
    UpdateMessage checkUpdate(StackoverflowContent newContent, StackoverflowContent oldContent);
}
