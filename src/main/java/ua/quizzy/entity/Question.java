package ua.quizzy.entity;

import lombok.Data;
import lombok.ToString;
import ua.quizzy.domain.question_parameters.Category;
import ua.quizzy.domain.question_parameters.Difficulty;
import ua.quizzy.domain.question_parameters.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "questions")
@ToString(exclude = "answers")
public class Question extends BaseEntity {
    private String question;
    private Type type;
    private Difficulty difficulty;
    private Category category;

    @ManyToOne
    @JoinColumn(name = "quiz_uuid", nullable = false)
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        var other = (Question) o;
        return Objects.equals(getUuid(), other.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
