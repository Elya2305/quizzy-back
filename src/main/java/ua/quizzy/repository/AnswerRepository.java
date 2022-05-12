package ua.quizzy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.quizzy.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, String> {
}
