package ua.quizzy.domain.statistics;

import lombok.Builder;
import lombok.Data;
import ua.quizzy.domain.question_parameters.Difficulty;

@Data
@Builder
public class HistoryEntry {
    private String timestamp;
    private Difficulty difficulty;
    private int score;
}
