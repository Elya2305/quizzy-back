package ua.quizzy.exception.custom;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends QuizException {
    public EntityNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
