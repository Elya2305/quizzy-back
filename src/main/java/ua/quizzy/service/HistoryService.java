package ua.quizzy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.quizzy.auth.context.UserContext;
import ua.quizzy.domain.QuizStatus;
import ua.quizzy.domain.statistics.HistoryEntry;
import ua.quizzy.domain.statistics.QuizGlobalStatistics;
import ua.quizzy.domain.statistics.QuizStatistics;
import ua.quizzy.entity.Quiz;
import ua.quizzy.entity.User;
import ua.quizzy.repository.QuestionAnswerRepository;
import ua.quizzy.repository.QuizRepository;
import ua.quizzy.repository.UserRepository;
import ua.quizzy.repository.data.StatisticsData;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuestionAnswerRepository questionAnswerRepository;

    public QuizStatistics getStatistics() {
        User userReference = userRepository.getById(UserContext.getUserUuid());

        int totalQuizzes = quizRepository.countAllByUser(userReference);
        int totalAnsweredQuestions = questionAnswerRepository.countAllByUser(userReference);
        int totalCorrectQuestions = questionAnswerRepository.countAllByUserAndCorrect(userReference, true);
        double percentageCorrect = ((double) totalCorrectQuestions / Math.max(totalAnsweredQuestions, 1)) * 100;

        return QuizStatistics.builder()
                .totalQuizzes(totalQuizzes)
                .totalAnsweredQuestions(totalAnsweredQuestions)
                .totalCorrectQuestions(totalCorrectQuestions)
                .percentageOfCorrectQuestions(percentageCorrect)
                .build();
    }

    //     todo hibernate fetches user ????
    public List<HistoryEntry> getHistory() {
        User userReference = userRepository.getById(UserContext.getUserUuid());

        return quizRepository.getAllByUserAndStatus(userReference, QuizStatus.FINISHED)
                .stream().map(this::map)
                .collect(Collectors.toList());
    }


    public List<QuizGlobalStatistics> getGlobalStatistics() {
        return quizRepository.getGlobalStatistics()
                .stream().map(this::map)
                .collect(Collectors.toList());
    }

    private QuizGlobalStatistics map(StatisticsData source) {
        return QuizGlobalStatistics.builder()
                .userUuid(source.getUserUuid())
                .userName(source.getFirstName())
                .totalQuizzes(source.getTotalQuizzes())
                .totalScore(source.getTotalScore())
                .build();
    }

    private HistoryEntry map(Quiz quiz) {
        return HistoryEntry.builder()
                .difficulty(quiz.getDifficulty())
                .score(quiz.getScore())
                .timestamp(quiz.getCreatedAt().toString())
                .build();
    }
}
