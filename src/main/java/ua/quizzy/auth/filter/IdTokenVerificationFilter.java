package ua.quizzy.auth.filter;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ua.quizzy.auth.context.UserContext;
import ua.quizzy.domain.user.SocialUser;
import ua.quizzy.exception.custom.AuthenticationException;
import ua.quizzy.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;
import static java.util.Objects.isNull;

@Log4j2
@Component
public class IdTokenVerificationFilter extends OncePerRequestFilter {
    private final static String HEADER_NAME = "Authorization-Google";
    private final GoogleIdTokenVerifier verifier;
    private final UserService userService;
    private final HandlerExceptionResolver exceptionResolver;

    public IdTokenVerificationFilter(GoogleIdTokenVerifier verifier, UserService userService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.verifier = verifier;
        this.userService = userService;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            doFilter(request, response, filterChain);
            filterChain.doFilter(request, response);
            UserContext.removeUserUuid();
        } catch (Exception exc) {
            exceptionResolver.resolveException(request, response, null, exc);
        }
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        if ("OPTIONS".equals(request.getMethod())) {
            return;
        }

        String idTokenValue = request.getHeader(HEADER_NAME);
        if (isNullOrEmpty(idTokenValue)) {
            throw new AuthenticationException("Id token is missing");
        }

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(idTokenValue);
        } catch (Exception e) {
            throw new AuthenticationException("Incorrect id token was provided");
        }
        if (isNull(idToken)) {
            throw new AuthenticationException("Id token is invalid");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();
        boolean emailVerified = payload.getEmailVerified();
        if (!emailVerified) {
            throw new AuthenticationException("Email is not verified");
        }
        String userUuid = userService.createOrUpdate(map(payload));
        UserContext.setUserUuid(userUuid);
    }

    private SocialUser map(GoogleIdToken.Payload payload) {
        return SocialUser.builder()
                .email(payload.getEmail())
                .firstName((String) payload.get("given_name"))
                .lastName((String) payload.get("family_name"))
                .pictureUrl((String) payload.get("picture"))
                .userId(payload.getSubject())
                .build();
    }
}
