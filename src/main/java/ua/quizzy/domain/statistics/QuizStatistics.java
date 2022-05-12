package ua.quizzy.domain.statistics;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizStatistics {
    private int totalQuizzes;
    private int totalAnsweredQuestions;
    private int totalCorrectQuestions;
    private double percentageOfCorrectQuestions;
}
