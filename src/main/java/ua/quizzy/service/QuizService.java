package ua.quizzy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.quizzy.auth.context.UserContext;
import ua.quizzy.domain.*;
import ua.quizzy.domain.question_parameters.Difficulty;
import ua.quizzy.entity.Quiz;
import ua.quizzy.entity.User;
import ua.quizzy.exception.custom.ActiveQuizExistsException;
import ua.quizzy.exception.custom.EntityNotFoundException;
import ua.quizzy.repository.QuestionAnswerRepository;
import ua.quizzy.repository.QuestionRepository;
import ua.quizzy.repository.QuizRepository;
import ua.quizzy.repository.UserRepository;

import java.util.List;

import static java.lang.Math.ceil;
import static ua.quizzy.domain.QuizStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final QuestionAnswerRepository questionAnswerRepository;

    //    todo transactional?
    public QuizResponse startQuiz(QuizParameters quizParameters) {
        validateActiveQuiz();

        Quiz quiz = new Quiz();
        quiz.setDifficulty(quizParameters.getDifficulty());
        quiz.setUser(userRepository.getById(UserContext.getUserUuid()));
        Quiz savedQuiz = quizRepository.save(quiz);

        QuizResponse response = new QuizResponse();
        response.setUuid(savedQuiz.getUuid());
        response.setQuestions(questionService.saveQuestions(quizParameters, savedQuiz));
        return response;
    }

    public ActiveQuiz getActiveQuizIfExist() {
        User userReference = userRepository.getById(UserContext.getUserUuid());

        return quizRepository.getQuizByStatus(userReference, ACTIVE)
                .map(this::mapActiveQuiz)
                .orElseGet(this::mapNonExistActiveQuiz);
    }

    public QuizResult finishQuiz(String quizUuid) {
        Quiz quiz = fetchFromDb(quizUuid);
        List<String> questionIds = questionRepository.getIdsByQuizUuid(quizUuid);

        int totalQuestions = questionIds.size();
        int totalCorrectQuestions = questionAnswerRepository.countCorrectQuestionAnswersByQuestionIds(questionIds);

        double percentageCorrect = ((double) totalCorrectQuestions / totalQuestions) * 100;
        int score = getScore(percentageCorrect);
        score = multiplyScoreDependingOnDifficulty(score, quiz.getDifficulty());

        quiz.setStatus(QuizStatus.FINISHED);
        quiz.setScore(score);
        quizRepository.save(quiz);

        return QuizResult.builder()
                .score(score)
                .totalCorrectQuestions(totalCorrectQuestions)
                .totalQuestions(totalQuestions)
                .quizUuid(quizUuid)
                .build();
    }

    public boolean checkActiveQuizExists(String userUuid) {
        User userReference = userRepository.getById(userUuid);
        return quizRepository.existsByUserAndStatus(userReference, ACTIVE);
    }

    private int getScore(double percentageCorrect) {
        if (percentageCorrect == 0) return 0;
        if (percentageCorrect < 10) return 1;
        if (percentageCorrect < 20) return 2;
        if (percentageCorrect < 30) return 3;
        if (percentageCorrect < 40) return 4;
        if (percentageCorrect < 50) return 5;
        if (percentageCorrect < 60) return 6;
        if (percentageCorrect < 70) return 7;
        if (percentageCorrect < 80) return 8;
        if (percentageCorrect < 100) return 9;
        return 10;
    }

    private int multiplyScoreDependingOnDifficulty(int score, Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return score;
            case MEDIUM:
                return (int) ceil(score * 1.2);
            case HARD:
                return (int) ceil(score * 1.5);
        }
        return score;
    }

    private ActiveQuiz mapActiveQuiz(Quiz source) {
        ActiveQuiz destination = new ActiveQuiz();
        destination.setExist(true);
        destination.setQuiz(mapQuizResponse(source));
        return destination;
    }

    private ActiveQuiz mapNonExistActiveQuiz() {
        ActiveQuiz destination = new ActiveQuiz();
        destination.setExist(false);
        return destination;
    }


    private QuizResponse mapQuizResponse(Quiz source) {
        QuizResponse destination = new QuizResponse();
        destination.setQuestions(questionService.getQuestions(source));
        destination.setUuid(source.getUuid());
        return destination;
    }

    private Quiz fetchFromDb(String quizUuid) {
        return quizRepository.findById(quizUuid)
                .orElseThrow(() -> new EntityNotFoundException("Can't find quiz with id " + quizUuid));
    }

    private void validateActiveQuiz() {
        if (checkActiveQuizExists(UserContext.getUserUuid())) {
            throw new ActiveQuizExistsException("Not finished quiz was found");
        }
    }
}
