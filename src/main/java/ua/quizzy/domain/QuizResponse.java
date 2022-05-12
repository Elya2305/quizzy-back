package ua.quizzy.domain;

import lombok.Data;

import java.util.List;

@Data
public class QuizResponse {
    private String uuid;
    private List<QuestionResponse> questions;
}
