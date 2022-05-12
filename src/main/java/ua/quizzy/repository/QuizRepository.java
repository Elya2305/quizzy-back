package ua.quizzy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.quizzy.domain.QuizStatus;
import ua.quizzy.entity.Quiz;
import ua.quizzy.entity.User;
import ua.quizzy.repository.data.StatisticsData;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, String> {

    int countAllByUser(User user);

    boolean existsByUserAndStatus(User user, QuizStatus status);

    default Optional<Quiz> getQuizByStatus(User user, QuizStatus status) {
        return getAllByUserAndStatus(user, status).stream().findAny();
    }

    @Query("select q from Quiz q where q.user.uuid =:userUuid and q.status =:status")
    List<Quiz> getQuizzesByStatus(String userUuid, QuizStatus status);

    List<Quiz> getAllByUserAndStatus(User user, QuizStatus status);

    @Query(value = "select u.first_name as firstName, u.uuid as userUuid, count(q.uuid) as totalQuizzes, coalesce(sum(q.score), 0) as totalScore " +
            "from users u left join quizzes q on u.uuid = q.user_uuid " +
            "group by u.uuid " +
            "order by totalScore desc", nativeQuery = true)
    List<StatisticsData> getGlobalStatistics();

    @Query("select count(q)>0 from Quiz q where q.uuid =:quizUuid and q.status = 'FINISHED'")
    boolean quizIsFinished(String quizUuid);
}
