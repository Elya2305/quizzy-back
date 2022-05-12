package ua.quizzy.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class QuizException extends ResponseStatusException {
    private final String message;

    public QuizException(HttpStatus status, String message) {
        super(status);
        this.message = message;
    }
}
