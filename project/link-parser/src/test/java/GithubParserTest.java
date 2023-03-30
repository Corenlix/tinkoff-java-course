import ParseProcessor.GitHubProcessor;
import ParseResponse.GitHubResponse;
import org.junit.jupiter.api.Test;

import java.net.URI;
import static org.assertj.core.api.Assertions.assertThat;

public class GithubParserTest {
    private final GitHubProcessor gitHubProcessor = new GitHubProcessor();

    @Test
    void github_correctLink() {
        //given
        var link = URI.create("https://github.com/Corenlix/tinkoff-java-course");
        var linkResponse = new GitHubResponse("Corenlix", "tinkoff-java-course");

        //when
        var response = gitHubProcessor.parse(link);

        //then
        assertThat(response).isEqualTo(linkResponse);
    }

    @Test
    void github_incorrectLink() {
        //given
        var link1 = URI.create("https://github.com/Corenlix/tinkoff-java-course/23");
        var link2 = URI.create("https://github.com/rer");
        var link3 = URI.create("https://stackoverflow.com/questions/1149703/how-can-i-convert-a-stack-trace-to-a-string");
        var link4 = URI.create("https://stackoverflow.com/question/wewe");
        var link5 = URI.create("https://google.com/");

        //when
        var response1 = gitHubProcessor.parse(link1);
        var response2 = gitHubProcessor.parse(link2);
        var response3 = gitHubProcessor.parse(link3);
        var response4 = gitHubProcessor.parse(link4);
        var response5 = gitHubProcessor.parse(link5);

        //then
        assertThat(response1).isNull();
        assertThat(response2).isNull();
        assertThat(response3).isNull();
        assertThat(response4).isNull();
        assertThat(response5).isNull();
    }
}
