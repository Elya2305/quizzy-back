package ua.quizzy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.quizzy.client.QuizApiClient;
import ua.quizzy.domain.*;
import ua.quizzy.entity.Question;
import ua.quizzy.entity.QuestionAnswer;
import ua.quizzy.entity.Quiz;
import ua.quizzy.exception.custom.QuizIsNotFinishedException;
import ua.quizzy.repository.QuestionAnswerRepository;
import ua.quizzy.repository.QuestionRepository;
import ua.quizzy.repository.QuizRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizApiClient quizApiClient;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final QuizRepository quizRepository;

    public List<QuestionResponse> saveQuestions(QuizParameters quizParameters, Quiz quiz) {
        QuestionClientList apiQuestions = quizApiClient.getQuestions(quizParameters);

        List<Question> questions = apiQuestions.getResults().stream().map(q -> {
            Question question = new Question();
            question.setQuestion(q.getQuestion());
            question.setCategory(quizParameters.getCategory());
            question.setType(quizParameters.getType());
            question.setDifficulty(quizParameters.getDifficulty());
            question.setQuiz(quiz);
            question.setAnswers(mapAnswers(q.getIncorrectAnswers(), q.getCorrectAnswer(), question));
            return question;
        }).collect(Collectors.toList());

        List<Question> savedQuestions = questionRepository.saveAll(questions);
        return map(savedQuestions);
    }

    public List<QuestionResponse> getQuestions(Quiz quiz) {
        List<QuestionResponse> questions = map(questionRepository.findAllByQuiz(quiz));
        questions.stream().flatMap(o -> o.getAnswers().stream()).forEach(answer -> {
            answer.setAnswered(questionAnswerRepository.existsByAnswer(answer.getUuid()));
        });
        return questions;
    }

    public List<QuestionWithCorrectAnswer> getCorrectQuestions(String quizUuid) {
        validateQuizIsFinished(quizUuid);
        Quiz quizReference = quizRepository.getById(quizUuid);

        return questionRepository.findAllByQuiz(quizReference)
                .stream().map(this::mapCorrect)
                .collect(Collectors.toList());
    }

    private QuestionWithCorrectAnswer mapCorrect(Question source) {
        QuestionWithCorrectAnswer destination = new QuestionWithCorrectAnswer();
        destination.setUuid(source.getUuid());
        destination.setQuestion(source.getQuestion());
        destination.setCorrectAnswer(mapCorrect(source.getAnswers()));
        destination.setAnsweredAnswer(mapAnswered(source));
        return destination;
    }

    private QuestionAnswerResponse mapAnswered(Question source) {
        return questionAnswerRepository.findByQuestion(source).map(this::mapAnswered)
                .orElse(new QuestionAnswerResponse());
    }

    private QuestionAnswerResponse mapAnswered(QuestionAnswer source) {
        QuestionAnswerResponse destination = new QuestionAnswerResponse();
        destination.setUuid(source.getAnswer().getUuid());
        destination.setAnswer(source.getAnswer().getAnswer());
        return destination;
    }

    private QuestionAnswerResponse mapCorrect(List<ua.quizzy.entity.Answer> source) {
        return source.stream().filter(ua.quizzy.entity.Answer::isCorrect)
                .map(this::mapCorrect)
                .findAny()
                .orElse(null);
    }

    private QuestionAnswerResponse mapCorrect(ua.quizzy.entity.Answer source) {
        QuestionAnswerResponse destination = new QuestionAnswerResponse();
        destination.setAnswer(source.getAnswer());
        destination.setUuid(source.getUuid());
        return destination;
    }

    private List<ua.quizzy.entity.Answer> mapAnswers(List<String> incorrectSource, String correctSource, Question question) {
        List<ua.quizzy.entity.Answer> destination = incorrectSource.stream()
                .map((o) -> mapIncorrectAnswer(o, question))
                .collect(Collectors.toList());

        ua.quizzy.entity.Answer correctDestination = new ua.quizzy.entity.Answer();
        correctDestination.setCorrect(true);
        correctDestination.setAnswer(correctSource);
        correctDestination.setQuestion(question);

        destination.add(correctDestination);
        return destination;
    }

    private ua.quizzy.entity.Answer mapIncorrectAnswer(String source, Question question) {
        ua.quizzy.entity.Answer answer = new ua.quizzy.entity.Answer();
        answer.setAnswer(source);
        answer.setQuestion(question);
        return answer;
    }

    private List<QuestionResponse> map(List<Question> savedQuestions) {
        return savedQuestions.stream().map(this::map).collect(Collectors.toList());
    }

    private QuestionResponse map(Question source) {
        QuestionResponse destination = new QuestionResponse();
        destination.setDifficulty(source.getDifficulty());
        destination.setUuid(source.getUuid());
        destination.setQuestion(source.getQuestion());
        destination.setCategory(source.getCategory());
        destination.setType(source.getType());
        destination.setAnswers(mapAnswers(source.getAnswers()));
        return destination;
    }

    private List<AnswerResponse> mapAnswers(List<ua.quizzy.entity.Answer> answers) {
        return answers.stream().map(this::mapAnswer).collect(Collectors.toList());
    }

    private AnswerResponse mapAnswer(ua.quizzy.entity.Answer source) {
        AnswerResponse destination = new AnswerResponse();
        destination.setUuid(source.getUuid());
        destination.setAnswer(source.getAnswer());
        return destination;
    }

    private void validateQuizIsFinished(String quizUuid) {
        if (!quizRepository.quizIsFinished(quizUuid)) {
            throw new QuizIsNotFinishedException("Quiz is not finished!");
        }
    }
}
