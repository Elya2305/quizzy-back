package ua.quizzy.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizResult {
    private String quizUuid;
    private int score;
    private int totalQuestions;
    private int totalCorrectQuestions;
}
