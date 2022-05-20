package ua.quizzy.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ua.quizzy.client.QuizApiClient;
import ua.quizzy.domain.*;
import ua.quizzy.service.QuestionService;
import ua.quizzy.service.QuizService;

import java.util.List;

// todo improve logs
@Slf4j
@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final QuestionService questionService;
    private final QuizApiClient quizApiClient;

    @PostMapping("/start")
    public QuizResponse startQuiz(@RequestBody QuizParameters quizParameters) {
        log.info("startQuiz");
        return quizService.startQuiz(quizParameters);
    }

    @PatchMapping("/finish/{quizUuid}")
    public QuizResult finishQuiz(@PathVariable String quizUuid) {
        log.info("finishQuiz");
        return quizService.finishQuiz(quizUuid);
    }

    @GetMapping("/active")
    public ActiveQuiz getActiveQuizIfExist() {
        log.info("getActiveQuizIfExist");
        return quizService.getActiveQuizIfExist();
    }

    @GetMapping("/correct-answers/{quizUuid}")
    public List<QuestionWithCorrectAnswer> getCorrectAnswers(@PathVariable String quizUuid) {
        log.info("getCorrectAnswers");
        return questionService.getCorrectQuestions(quizUuid);
    }
}
