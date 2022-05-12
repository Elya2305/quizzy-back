package ua.quizzy.domain;

import lombok.Data;

@Data
public class ActiveQuiz {
    private boolean exist;
    private QuizResponse quiz;
}
