package ua.quizzy.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Data
@Entity
@Table(name = "question_answers")
public class QuestionAnswer extends BaseEntity {

    private boolean correct;
    @ManyToOne
    @JoinColumn(name = "question_uuid", nullable = false)
    private Question question;
    @ManyToOne
    @JoinColumn(name = "answer_uuid", nullable = false)
    private Answer answer;
    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionAnswer)) return false;
        var other = (QuestionAnswer) o;
        return Objects.equals(getUuid(), other.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
