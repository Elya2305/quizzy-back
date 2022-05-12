package ua.quizzy.exception.custom;

import org.springframework.http.HttpStatus;

public class QuizIsNotFinishedException extends QuizException {
    public QuizIsNotFinishedException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
