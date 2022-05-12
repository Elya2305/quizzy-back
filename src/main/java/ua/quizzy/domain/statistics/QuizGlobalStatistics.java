package ua.quizzy.domain.statistics;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizGlobalStatistics {
    private String userUuid;
    private String userName;
    private int totalQuizzes;
    private int totalScore;
}
