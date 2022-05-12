package ua.quizzy.domain.question_parameters;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    MULTIPLE_CHOICE("multiple"),
    TRUE_OR_FALSE("boolean");

    private String parameter;
}
