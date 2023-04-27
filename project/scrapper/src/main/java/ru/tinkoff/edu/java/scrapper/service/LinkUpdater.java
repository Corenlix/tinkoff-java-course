package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;

public interface LinkUpdater {
    LinkEntity update(LinkEntity link);
}
