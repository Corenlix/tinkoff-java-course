package ru.tinkoff.edu.java.scrapper.model.linkcontent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public final class GithubContent implements LinkContent {
    OffsetDateTime updatedAt;
}
