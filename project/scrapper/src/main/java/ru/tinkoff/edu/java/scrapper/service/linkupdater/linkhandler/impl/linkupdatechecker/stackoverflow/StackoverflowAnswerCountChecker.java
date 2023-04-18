package ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler.impl.linkupdatechecker.stackoverflow;

import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.model.linkcontent.StackoverflowContent;

@Component
public class StackoverflowAnswerCountChecker implements StackOverflowUpdateChecker {
    @Override
    public UpdateMessage checkUpdate(StackoverflowContent newContent, StackoverflowContent oldContent) {
        if (newContent.getAnswerCount() == oldContent.getAnswerCount()) {
            return null;
        }

        return new UpdateMessage("- Появились новые ответы");
    }
}
