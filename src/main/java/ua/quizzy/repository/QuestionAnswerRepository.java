package ua.quizzy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.quizzy.entity.QuestionAnswer;
import ua.quizzy.entity.User;

import java.util.List;
import java.util.Optional;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, String> {
    @Query("select count(qa) from QuestionAnswer qa where qa.question.uuid in (:questionIds) and qa.correct = true")
    int countCorrectQuestionAnswersByQuestionIds(List<String> questionIds);

    @Query("select qa from QuestionAnswer qa where qa.question.uuid =:questionUuid")
    Optional<QuestionAnswer> getByQuestion(String questionUuid);

    //    todo check
    @Query("select count(qa)>0 from QuestionAnswer qa where qa.answer.uuid =:answerUuid")
    boolean existsByAnswer(String answerUuid);

    int countAllByUser(User user);

    int countAllByUserAndCorrect(User user, boolean correct);
}
