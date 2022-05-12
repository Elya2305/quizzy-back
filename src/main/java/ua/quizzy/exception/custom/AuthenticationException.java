package ua.quizzy.exception.custom;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class AuthenticationException extends QuizException {
    public AuthenticationException(String message) {
        super(FORBIDDEN, message);
    }
}
