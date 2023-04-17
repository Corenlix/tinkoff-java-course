package ru.tinkoff.edu.java.scrapper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;
import ru.tinkoff.edu.java.scrapper.dto.links.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.links.ListLinksResponse;

import java.net.URI;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LinkMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "url", target = "url")
    LinkResponse toLinkResponse(LinkDto linkDto);

    default ListLinksResponse toListLinksResponse(List<LinkDto> linkDtos) {
        return new ListLinksResponse(
                linkDtos.stream().map(this::toLinkResponse).toList(),
                linkDtos.size());
    }

    default URI toUri(String url) {
        return URI.create(url);
    }
}
