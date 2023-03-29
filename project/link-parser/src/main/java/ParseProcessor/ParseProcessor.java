package ParseProcessor;

import ParseResponse.ParseResponse;

import java.net.URI;

public sealed interface ParseProcessor permits GitHubProcessor, StackOverflowProcessor {
    ParseResponse parse(URI link);
}
