package ru.tinkoff.edu.java.scrapper.linkupdatechecker.stackoverflow;

import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.model.linkcontent.StackoverflowContent;

@Component
public class StackoverflowAnsweredStatusChecker implements StackOverflowUpdateChecker{
    @Override
    public UpdateMessage checkUpdate(StackoverflowContent newContent, StackoverflowContent oldContent) {
        if (newContent.isAnswered() != oldContent.isAnswered())
            return new UpdateMessage("Обновился статус ответа!");

        return null;
    }
}
