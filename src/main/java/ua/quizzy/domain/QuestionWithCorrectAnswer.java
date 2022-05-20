package ua.quizzy.domain;

import lombok.Data;

@Data
public class QuestionWithCorrectAnswer {
    private String uuid;
    private String question;
    private QuestionAnswerResponse correctAnswer;
    private QuestionAnswerResponse answeredAnswer;
}
