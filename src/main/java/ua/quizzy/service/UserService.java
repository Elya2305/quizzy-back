package ua.quizzy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.quizzy.auth.context.UserContext;
import ua.quizzy.domain.user.SocialUser;
import ua.quizzy.domain.user.UserProfile;
import ua.quizzy.entity.User;
import ua.quizzy.exception.custom.EntityNotFoundException;
import ua.quizzy.repository.UserRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // todo cache?
    public synchronized String createOrUpdate(SocialUser socialUser) {
        User user = userRepository.findByUserId(socialUser.getUserId()).orElseGet(User::new);
        user.setEmail(socialUser.getEmail());
        user.setFirstName(socialUser.getFirstName());
        user.setLastName(socialUser.getLastName());
        user.setPictureUrl(socialUser.getPictureUrl());
        user.setUserId(socialUser.getUserId());

        return userRepository.save(user).getUuid();
    }

    public UserProfile getProfile() {
        String userUuid = UserContext.getUserUuid();

        return map(userRepository
                .findById(userUuid)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with id %s not found", userUuid))));
    }

    private UserProfile map(User source) {
        UserProfile destination = new UserProfile();
        destination.setEmail(source.getEmail());
        destination.setUuid(source.getUuid());
        destination.setFirstName(source.getFirstName());
        destination.setPictureUrl(source.getPictureUrl());
        return destination;
    }

}
