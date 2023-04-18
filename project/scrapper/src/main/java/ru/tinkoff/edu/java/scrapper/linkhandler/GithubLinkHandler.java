package ru.tinkoff.edu.java.scrapper.linkhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import parseresponse.GitHubResponse;
import parseresponse.ParseResponse;
import ru.tinkoff.edu.java.scrapper.dto.UpdateMessage;
import ru.tinkoff.edu.java.scrapper.dto.client.github.GitHubRepositoryResponse;
import ru.tinkoff.edu.java.scrapper.httpclient.GitHubClient;
import ru.tinkoff.edu.java.scrapper.linksupdateschecker.github.GithubLinksUpdateChecker;
import ru.tinkoff.edu.java.scrapper.model.linkcontent.GithubContent;
import ru.tinkoff.edu.java.scrapper.model.linkcontent.LinkContent;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GithubLinkHandler implements LinkHandler{
    private final GitHubClient gitHubClient;
    private final List<GithubLinksUpdateChecker> updateCheckers;

    @Override
    public boolean canHandle(ParseResponse parseResponse) {
        return parseResponse.getClass().equals(GitHubResponse.class);
    }

    @Override
    public LinkContent getContent(ParseResponse parseResponse) {
        GitHubResponse gitHubResponse = (GitHubResponse) parseResponse;
        GitHubRepositoryResponse repository = gitHubClient.fetchRepository(gitHubResponse.repository(), gitHubResponse.user());
        return getLinkContent(repository);
    }

    @Override
    @SneakyThrows
    public List<UpdateMessage> getUpdates(LinkContent newContent, String oldContentJson) {
        var oldGithubContent = new ObjectMapper().readValue(oldContentJson, GithubContent.class);
        var newGithubContent = (GithubContent) newContent;

        return updateCheckers.stream()
                .map(updatesChecker -> updatesChecker.checkUpdate(newGithubContent, oldGithubContent))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private GithubContent getLinkContent(GitHubRepositoryResponse gitHubRepository) {
        return new GithubContent(gitHubRepository.updatedAt());
    }
}