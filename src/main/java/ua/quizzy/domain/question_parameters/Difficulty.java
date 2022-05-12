package ua.quizzy.domain.question_parameters;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Difficulty {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    private String parameter;
}
