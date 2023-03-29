import ParseChain.ParseChain;
import ParseProcessor.GitHubProcessor;
import ParseProcessor.StackOverflowProcessor;
import ParseResponse.ParseResponse;

import java.net.URI;

public final class LinkParser {
    private final ParseChain parseChain;

    public LinkParser() {
        parseChain = new ParseChain();
        parseChain.addProcessor(new GitHubProcessor());
        parseChain.addProcessor(new StackOverflowProcessor());
    }

    public ParseResponse parse(URI link) {
        return parseChain.parse(link);
    }
}
