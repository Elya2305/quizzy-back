package ua.quizzy.entity;

import lombok.Data;
import ua.quizzy.domain.QuizStatus;
import ua.quizzy.domain.question_parameters.Difficulty;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "quizzes")
public class Quiz extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty; // todo potentially removable

    @Enumerated(EnumType.STRING)
    private QuizStatus status = QuizStatus.ACTIVE;

    private int score;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;
        var other = (Quiz) o;
        return Objects.equals(getUuid(), other.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
