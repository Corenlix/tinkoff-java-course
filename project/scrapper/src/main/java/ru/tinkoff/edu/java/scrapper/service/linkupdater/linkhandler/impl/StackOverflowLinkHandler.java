package ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import parseresponse.ParseResponse;
import parseresponse.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.dto.client.stackexchange.StackExchangeQuestionResponse;
import ru.tinkoff.edu.java.scrapper.httpclient.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler.LinkHandler;
import ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler.impl.linkupdatechecker.stackoverflow.StackOverflowUpdateChecker;
import ru.tinkoff.edu.java.scrapper.model.linkcontent.LinkContent;
import ru.tinkoff.edu.java.scrapper.model.linkcontent.StackoverflowContent;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StackOverflowLinkHandler implements LinkHandler {
    private final StackOverflowClient stackOverflowClient;
    private final List<StackOverflowUpdateChecker> updateCheckers;

    @Override
    public boolean canHandle(ParseResponse parseResponse) {
        return parseResponse.getClass().equals(StackOverflowResponse.class);
    }

    @Override
    public LinkContent getContent(ParseResponse parseResponse) {
        StackOverflowResponse stackOverflowResponse = (StackOverflowResponse) parseResponse;
        StackExchangeQuestionResponse question = stackOverflowClient.fetchQuestion(stackOverflowResponse.id());
        return getLinkContent(question);
    }

    @Override
    public LinkContent getContentFromJson(String json) {
        return LinkContent.fromJson(json, StackoverflowContent.class);
    }

    @SneakyThrows
    @Override
    public List<UpdateMessage> getUpdates(LinkContent newContent, LinkContent oldContent) {
        var oldStackoverflowContent = (StackoverflowContent) oldContent;
        var newStackoverflowContent = (StackoverflowContent) newContent;

        return updateCheckers.stream()
                .map(updatesChecker -> updatesChecker.checkUpdate(newStackoverflowContent, oldStackoverflowContent))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    StackoverflowContent getLinkContent(StackExchangeQuestionResponse questionResponse) {
        return new StackoverflowContent(questionResponse.isAnswered(), questionResponse.answerCount());
    }
}
