package ru.tinkoff.edu.java.scrapper.linksupdateschecker.stackoverflow;

import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.model.linkcontent.StackoverflowContent;

public interface StackOverflowUpdateChecker {
    UpdateMessage checkUpdate(StackoverflowContent newContent, StackoverflowContent oldContent);
}
