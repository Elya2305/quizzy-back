package ua.quizzy.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Data
@Entity
@Table(name = "answers")
@ToString(exclude = "question")
public class Answer extends BaseEntity {
    private String answer;
    private boolean correct;
    @ManyToOne
    @JoinColumn(name = "question_uuid", nullable = false)
    private Question question;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        var other = (Answer) o;
        return Objects.equals(getUuid(), other.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
