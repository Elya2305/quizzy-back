package ua.quizzy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.quizzy.entity.Question;
import ua.quizzy.entity.Quiz;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, String> {

    @Query("select q.uuid from Question q where q.quiz.uuid =:quizUuid")
    List<String> getIdsByQuizUuid(String quizUuid);

    List<Question> findAllByQuiz(Quiz quiz);
}
