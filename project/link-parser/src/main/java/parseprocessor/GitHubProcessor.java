package parseprocessor;

import java.net.URI;
import org.jetbrains.annotations.Nullable;
import parseresponse.GitHubResponse;

public final class GitHubProcessor implements ParseProcessor {
    private static final String GITHUB_DOMAIN = "github.com";
    private static final int PATH_SEGMENTS_COUNT = 3;

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
        if (pathSegments.length != PATH_SEGMENTS_COUNT) {
            return null;
        }
        String username = pathSegments[1];
        String repositoryName = pathSegments[2];

        return new GitHubResponse(username, repositoryName);
    }
}
