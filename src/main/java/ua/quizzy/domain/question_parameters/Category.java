package ua.quizzy.domain.question_parameters;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    GENERAL_KNOWLEDGE(9, "General knowledge"),
    SPORTS(21, "Sports"),
    BOOKS(10, "Books"),
    FILM(11, "Films"),
    MUSIC(12, "Music"),
    VIDEO_GAMES(15, "Video games"),
    ANIMALS(27, "Animals"),
    ANIME(31, "Anime"),
    HISTORY(23, "History"),
    POLITICS(24, "Politics"),
    MATH(19, "Math"),
    COMPUTERS(18, "Computers");

    private int parameter;
    private String displayName;
}
