package ParseProcessor;

import ParseResponse.StackOverflowResponse;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.URISyntaxException;

public final class StackOverflowProcessor implements ParseProcessor {
    private static final String STACKOVERFLOW_DOMAIN = "stackoverflow.com";
    private static final String QUESTIONS_SECTION = "questions";

    @Override
    public StackOverflowResponse parse(String link) {
        try {
            URI uri = new URI(link);
            if (!isStackOverflowDomain(uri)) {
                return null;
            }

            return extractDataFromUri(uri);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    private static boolean isStackOverflowDomain(URI uri) {
        return uri.getAuthority().equals(STACKOVERFLOW_DOMAIN);
    }

    @Nullable
    private static StackOverflowResponse extractDataFromUri(URI uri) {
        String[] pathSegments = uri.getPath().split("/");
        if (pathSegments.length != 4 || !pathSegments[1].equals(QUESTIONS_SECTION)) {
            return null;
        }
        int id = Integer.parseInt(pathSegments[2]);

        return new StackOverflowResponse(id);
    }
}
