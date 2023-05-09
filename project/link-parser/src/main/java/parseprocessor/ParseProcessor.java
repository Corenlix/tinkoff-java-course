package parseprocessor;

import java.net.URI;
import parseresponse.ParseResponse;

public sealed interface ParseProcessor permits GitHubProcessor, StackOverflowProcessor {
    ParseResponse parse(URI link);
}
