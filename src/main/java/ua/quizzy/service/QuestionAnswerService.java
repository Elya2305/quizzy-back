package ua.quizzy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.quizzy.auth.context.UserContext;
import ua.quizzy.domain.QuestionAnswerRequest;
import ua.quizzy.entity.Answer;
import ua.quizzy.entity.QuestionAnswer;
import ua.quizzy.repository.AnswerRepository;
import ua.quizzy.repository.QuestionAnswerRepository;
import ua.quizzy.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class QuestionAnswerService {
    private final QuestionAnswerRepository questionAnswerRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    // todo extra select statement
    public void saveQuestionAnswer(QuestionAnswerRequest source) {
        QuestionAnswer entity = fetchFromDbOrCreate(source.getQuestionUuid());
        Answer answer = answerRepository.getById(source.getAnswerUuid());
        entity.setAnswer(answer);
        entity.setQuestion(answer.getQuestion());
        entity.setUser(userRepository.getById(UserContext.getUserUuid()));
        entity.setCorrect(answer.isCorrect());
        questionAnswerRepository.save(entity);
    }

    private QuestionAnswer fetchFromDbOrCreate(String questionUuid) {
        return questionAnswerRepository.getByQuestion(questionUuid)
                .orElseGet(QuestionAnswer::new);
    }
}
