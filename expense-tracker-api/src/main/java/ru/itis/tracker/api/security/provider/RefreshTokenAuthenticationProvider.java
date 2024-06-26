package ru.itis.tracker.api.security.provider;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.itis.tracker.api.security.authentication.RefreshTokenAuthentication;
import ru.itis.tracker.api.security.exception.RefreshTokenException;
import ru.itis.tracker.api.security.util.AuthenticationUtils;

@RequiredArgsConstructor
@Component
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationUtils authenticationUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String refreshTokenValue = (String) authentication.getCredentials();

        try {
            return authenticationUtils.buildAuthentication(refreshTokenValue);
        } catch (JWTVerificationException e) {
            throw new RefreshTokenException(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshTokenAuthentication.class.isAssignableFrom(authentication);
    }
}
