package ua.quizzy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.quizzy.domain.CategoryResponse;
import ua.quizzy.domain.question_parameters.Category;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    public List<CategoryResponse> getCategories() {
        return Arrays.stream(Category.values())
                .map(this::map)
                .collect(Collectors.toList());
    }

    private CategoryResponse map(Category source) {
        CategoryResponse destination = new CategoryResponse();
        destination.setDisplayTitle(source.getDisplayName());
        destination.setValue(source);
        return destination;
    }
}
