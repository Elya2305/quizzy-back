package ua.quizzy.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
    private int status;
    private String message;
}
