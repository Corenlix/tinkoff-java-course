package linkparser;

import java.net.URI;
import parsechain.ParseChain;
import parseprocessor.GitHubProcessor;
import parseprocessor.StackOverflowProcessor;
import parseresponse.ParseResponse;

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
