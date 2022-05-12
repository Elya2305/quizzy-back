package ua.quizzy.auth.context;

import lombok.Data;

@Data
public class UserContext {
    private static ThreadLocal<String> userUuid = new ThreadLocal<>();

    public static void setUserUuid(String userUuid) {
        UserContext.userUuid.set(userUuid);
    }

    public static void removeUserUuid() {
        UserContext.userUuid.remove();
    }

    public static String getUserUuid() {
        return UserContext.userUuid.get();
    }
}
