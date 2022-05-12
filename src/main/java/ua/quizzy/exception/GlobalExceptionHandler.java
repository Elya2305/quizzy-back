package ua.quizzy.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.quizzy.exception.custom.QuizException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalException(Exception e) {
        log.error("An exception was caught!", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .message("Internal exception occurred")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build());
    }

    @ExceptionHandler(QuizException.class)
    public ResponseEntity<ErrorResponse> handleSolarisbankApiException(QuizException e) {
        log.error("An exception was caught!", e);

        return ResponseEntity
                .status(e.getStatus().value())
                .header("Access-Control-Allow-Origin", "*")
//                .header("Access-Control-Allow-Methods", "DELETE, POST, GET, OPTIONS")
//                .header("Access-Control-Allow-Headers", "Content-Type, Authorization-Google X-Requested-With")
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .status(e.getStatus().value())
                        .build());
    }
}
