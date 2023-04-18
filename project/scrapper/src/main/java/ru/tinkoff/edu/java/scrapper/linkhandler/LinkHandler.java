package ru.tinkoff.edu.java.scrapper.linkhandler;

import parseresponse.ParseResponse;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.model.linkcontent.LinkContent;

import java.util.List;

public interface LinkHandler {
    boolean canHandle(ParseResponse parseResponse);
    LinkContent getContent(ParseResponse parseResponse);
    List<UpdateMessage> getUpdates(LinkContent newContent, String oldContentJson);
}
