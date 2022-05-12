package ua.quizzy.exception.custom;

import org.springframework.http.HttpStatus;

public class ActiveQuizExistsException extends QuizException {
    public ActiveQuizExistsException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
