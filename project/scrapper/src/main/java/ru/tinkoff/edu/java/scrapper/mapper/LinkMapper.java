package ru.tinkoff.edu.java.scrapper.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.edu.java.scrapper.domain.LinkEntity;
import ru.tinkoff.edu.java.scrapper.dto.controller.links.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.controller.links.ListLinksResponse;

import java.net.URI;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LinkMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "url", target = "url")
    LinkResponse toLinkResponse(LinkEntity linkEntity);

    default ListLinksResponse toListLinksResponse(List<LinkEntity> linkEntities) {
        return new ListLinksResponse(
                linkEntities.stream().map(this::toLinkResponse).toList(),
                linkEntities.size());
    }

    default URI toUri(String url) {
        return URI.create(url);
    }
}
