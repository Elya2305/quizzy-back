package ua.quizzy.domain.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SocialUser {
    private String pictureUrl;
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
}
