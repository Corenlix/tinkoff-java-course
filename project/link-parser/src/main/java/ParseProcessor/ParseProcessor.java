package ParseProcessor;

import ParseResponse.ParseResponse;

public sealed interface ParseProcessor permits GitHubProcessor, StackOverflowProcessor {
    ParseResponse parse(String link);
}
