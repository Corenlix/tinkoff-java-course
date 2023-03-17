package ParseProcessor;

import ParseResponse.GitHubResponse;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.URISyntaxException;

public final class GitHubProcessor implements ParseProcessor {
    private static final String GITHUB_DOMAIN = "github.com";

    @Override
    public GitHubResponse parse(String link) {
        try {
            URI uri = new URI(link);
            if (!isGitHubDomain(uri)) {
                return null;
            }

            return extractDataFromUri(uri);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    private static boolean isGitHubDomain(URI uri) {
        return uri.getAuthority().equals(GITHUB_DOMAIN);
    }

    @Nullable
    private static GitHubResponse extractDataFromUri(URI uri) {
        String[] pathSegments = uri.getPath().split("/");
        if (pathSegments.length != 3) {
            return null;
        }
        String username = pathSegments[1];
        String repositoryName = pathSegments[2];

        return new GitHubResponse(username, repositoryName);
    }
}
