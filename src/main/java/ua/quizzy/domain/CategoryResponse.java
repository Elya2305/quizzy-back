package ua.quizzy.domain;

import lombok.Data;
import ua.quizzy.domain.question_parameters.Category;

@Data
public class CategoryResponse {
    private Category value;
    private String displayTitle;
}
