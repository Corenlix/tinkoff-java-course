package ru.tinkoff.edu.java.scrapper.service.linkupdater.linkhandler;

import java.util.List;
import parseresponse.ParseResponse;
import ru.tinkoff.edu.java.scrapper.domain.linkcontent.LinkContent;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;

public interface LinkHandler {
    boolean canHandle(ParseResponse parseResponse);

    LinkContent getContent(ParseResponse parseResponse);

    LinkContent getContentFromJson(String json);

    List<UpdateMessage> getUpdates(LinkContent newContent, LinkContent oldContent);
}
