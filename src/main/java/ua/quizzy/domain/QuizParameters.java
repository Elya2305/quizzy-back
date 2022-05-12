package ua.quizzy.domain;

import lombok.Data;
import ua.quizzy.domain.question_parameters.Category;
import ua.quizzy.domain.question_parameters.Difficulty;
import ua.quizzy.domain.question_parameters.Type;

@Data
public class QuizParameters {
    private Difficulty difficulty;
    private Type type;
    private Category category;
    private int amount;
}
