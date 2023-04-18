package parseprocessor;

import parseresponse.StackOverflowResponse;
import org.jetbrains.annotations.Nullable;

import java.net.URI;

public final class StackOverflowProcessor implements ParseProcessor {
    private static final String STACKOVERFLOW_DOMAIN = "stackoverflow.com";
    private static final String QUESTIONS_SECTION = "questions";

    @Override
    public StackOverflowResponse parse(URI link) {
        if (!isStackOverflowDomain(link)) {
            return null;
        }

        return extractDataFromUri(link);
    }

    private boolean isStackOverflowDomain(URI link) {
        return link.getAuthority().equals(STACKOVERFLOW_DOMAIN);
    }

    @Nullable
    private StackOverflowResponse extractDataFromUri(URI link) {
        String[] pathSegments = link.getPath().split("/");
        if (pathSegments.length != 4 || !pathSegments[1].equals(QUESTIONS_SECTION)) {
            return null;
        }
        long id = Long.parseLong(pathSegments[2]);

        return new StackOverflowResponse(id);
    }
}
