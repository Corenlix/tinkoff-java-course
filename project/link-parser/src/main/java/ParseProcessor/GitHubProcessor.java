package ParseProcessor;

import ParseResponse.GitHubResponse;
import org.jetbrains.annotations.Nullable;

import java.net.URI;

public final class GitHubProcessor implements ParseProcessor {
    private static final String GITHUB_DOMAIN = "github.com";

    @Override
    public GitHubResponse parse(URI link) {
        if (!isGitHubDomain(link)) {
            return null;
        }

        return extractDataFromUri(link);
    }

    private boolean isGitHubDomain(URI link) {
        return link.getAuthority().equals(GITHUB_DOMAIN);
    }

    @Nullable
    private GitHubResponse extractDataFromUri(URI link) {
        String[] pathSegments = link.getPath().split("/");
        if (pathSegments.length != 3) {
            return null;
        }
        String username = pathSegments[1];
        String repositoryName = pathSegments[2];

        return new GitHubResponse(username, repositoryName);
    }
}
