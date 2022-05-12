package ua.quizzy.domain.user;

import lombok.Data;

@Data
public class UserProfile {
    private String uuid;
    private String email;
    private String firstName;
    private String pictureUrl;
}
