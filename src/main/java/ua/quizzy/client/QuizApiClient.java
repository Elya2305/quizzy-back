package ua.quizzy.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import ua.quizzy.domain.QuestionClientList;
import ua.quizzy.domain.QuizParameters;

@Component
@RequiredArgsConstructor
public class QuizApiClient {
    private final WebClient webClient;

    public QuestionClientList getQuestions(QuizParameters quizParameters) {
        return this.webClient
                .get()
                .uri(builder -> builder
                        .queryParam("amount", quizParameters.getAmount())
                        .queryParam("category", quizParameters.getCategory().getParameter())
                        .queryParam("difficulty", quizParameters.getDifficulty().getParameter())
                        .queryParam("type", quizParameters.getType().getParameter())
                        .build())
                .retrieve()
                .bodyToMono(QuestionClientList.class)
                .block();
    }
}
