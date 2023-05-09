package ru.tinkoff.edu.java.scrapper.domain.linkcontent;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public final class GithubContent implements LinkContent {
    OffsetDateTime pushedAt;
}
