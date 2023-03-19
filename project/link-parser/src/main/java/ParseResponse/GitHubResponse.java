package ParseResponse;

public record GitHubResponse(String user, String repository) implements ParseResponse {
}
