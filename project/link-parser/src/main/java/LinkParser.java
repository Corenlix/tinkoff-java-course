import ParseChain.ParseChain;
import ParseProcessor.GitHubProcessor;
import ParseProcessor.StackOverflowProcessor;
import ParseResponse.ParseResponse;

public final class LinkParser {
    private final ParseChain parseChain;

    public LinkParser() {
        parseChain = new ParseChain();
        parseChain.addProcessor(new GitHubProcessor());
        parseChain.addProcessor(new StackOverflowProcessor());
    }

    public ParseResponse parse(String link) {
        return parseChain.parse(link);
    }
}
