package ua.quizzy.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.quizzy.domain.QuestionAnswerRequest;
import ua.quizzy.service.QuestionAnswerService;

@Slf4j
@RestController
@RequestMapping("/question-answer")
@RequiredArgsConstructor
public class QuestionAnswerController {
    private final QuestionAnswerService questionAnswerService;

    @PostMapping
    public boolean saveQuestionAnswer(@RequestBody QuestionAnswerRequest questionAnswer) {
        log.info("saveQuestionAnswer");
        questionAnswerService.saveQuestionAnswer(questionAnswer);
        return true;
    }
}
