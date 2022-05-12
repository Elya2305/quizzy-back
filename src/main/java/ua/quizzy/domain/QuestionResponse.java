package ua.quizzy.domain;

import lombok.Data;
import ua.quizzy.domain.question_parameters.Category;
import ua.quizzy.domain.question_parameters.Difficulty;
import ua.quizzy.domain.question_parameters.Type;

import java.util.List;

@Data
public class QuestionResponse {
    private String uuid;
    private Category category;
    private Type type;
    private Difficulty difficulty;
    private String question;
    private List<AnswerResponse> answers;
}
