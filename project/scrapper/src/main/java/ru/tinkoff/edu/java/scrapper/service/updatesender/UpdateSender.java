package ru.tinkoff.edu.java.scrapper.service.updatesender;

import ru.tinkoff.edu.java.scrapper.dto.client.tgBot.LinkUpdate;

public interface UpdateSender {
    void sendUpdate(LinkUpdate update);
}
