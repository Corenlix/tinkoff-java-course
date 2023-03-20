package ru.tinkoff.edu.java.scrapper.httpclient;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.StackExchangeQuestionResponse;
import ru.tinkoff.edu.java.scrapper.dto.StackExchangeQuestionsResponse;

@Builder
@RequiredArgsConstructor
public class StackOverflowClientImpl implements StackOverflowClient {
    private final String site = "stackoverflow";
    private final String apiVersion;
    private final WebClient webClient;

    @Override
    public StackExchangeQuestionResponse fetchQuestion(Long id) {
        var questions = getResponse(StackExchangeQuestionsResponse.class,
                "questions",
                id.toString());
        return questions.questions().get(0);
    }

    private <T> T getResponse(Class<T> responseClass, String... params) {
        String paramsString = String.join("/", params);
        return webClient
                .get()
                .uri(String.format("/%s/%s?site=%s", apiVersion, paramsString, site))
                .retrieve()
                .bodyToMono(responseClass)
                .block();
    }
}
