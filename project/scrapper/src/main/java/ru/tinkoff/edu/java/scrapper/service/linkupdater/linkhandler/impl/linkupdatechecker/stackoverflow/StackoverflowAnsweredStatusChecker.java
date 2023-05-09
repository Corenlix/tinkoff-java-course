package ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler.impl.linkupdatechecker.stackoverflow;

import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.domain.linkcontent.StackoverflowContent;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;

@Component
public class StackoverflowAnsweredStatusChecker implements StackOverflowUpdateChecker {
    @Override
    public UpdateMessage checkUpdate(StackoverflowContent newContent, StackoverflowContent oldContent) {
        if (newContent.isAnswered() != oldContent.isAnswered()) {
            return new UpdateMessage("- Обновился статус ответа");
        }

        return null;
    }
}
