package ru.tinkoff.edu.java.scrapper.httpclient;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.client.stackexchange.StackExchangeQuestionResponse;
import ru.tinkoff.edu.java.scrapper.dto.client.stackexchange.StackExchangeQuestionsResponse;

import java.util.Arrays;
import java.util.stream.Stream;

@Builder
@RequiredArgsConstructor
public class StackOverflowClientImpl implements StackOverflowClient {
    private final String site = "stackoverflow";
    private final String apiVersion;
    private final WebClient webClient;

    @Override
    public StackExchangeQuestionResponse fetchQuestion(Long id) {
        return fetchQuestions(id).questions().get(0);
    }

    private StackExchangeQuestionsResponse fetchQuestions(Long... ids) {
        String[] params = Stream.concat(
                        Stream.of("questions"),
                        Arrays.stream(ids).map(String::valueOf))
                .toArray(String[]::new);

        return getResponse(StackExchangeQuestionsResponse.class, params);
    }

    private <T> T getResponse(Class<T> responseClass, String... params) {

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment(apiVersion)
                        .pathSegment(params)
                        .queryParam("site", site)
                        .build())
                .retrieve()
                .bodyToMono(responseClass)
                .block();
    }
}
