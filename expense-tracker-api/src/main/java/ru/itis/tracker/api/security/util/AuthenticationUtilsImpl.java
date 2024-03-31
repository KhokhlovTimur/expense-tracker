package ru.itis.tracker.api.security.util;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.itis.tracker.api.model.User;
import ru.itis.tracker.api.repository.tokens.TokenRepository;
import ru.itis.tracker.api.security.SecurityConstants;
import ru.itis.tracker.api.security.details.UserDetailsImpl;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static ru.itis.tracker.api.security.SecurityConstants.ROLE_PARAMETER;
import static ru.itis.tracker.api.security.SecurityConstants.USERNAME_PARAMETER;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationUtilsImpl implements AuthenticationUtils {

    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void setAuthentication(String token, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Если аксес токен в блек-листе - то нет доступа, если рефреш токена нет в репозитории - значит такой токен не выдавался пользователю
        if (!tokenRepository.isAccessTokenInBlackList(token) && !tokenRepository.isRefreshTokenExists(token)) {
            try {
                Authentication authentication = buildAuthentication(token);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JWTVerificationException e) {
                log.error(e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @Override
    public Authentication buildAuthentication(String token) throws JWTVerificationException {

        if (!tokenRepository.isAccessTokenInBlackList(token) || tokenRepository.isRefreshTokenExists(token)) {
            Map<String, String> data = jwtUtil.parse(token);
            UserDetails userDetails = new UserDetailsImpl(
                    User.builder()
                            .role(User.Role.valueOf(data.get(ROLE_PARAMETER)))
                            .email(data.get(USERNAME_PARAMETER))
                            .build()
            );
            return new UsernamePasswordAuthenticationToken(userDetails, null,
                    Collections.singleton(new SimpleGrantedAuthority(data.get(ROLE_PARAMETER))));
        } else {
            jwtUtil.parse("");
        }

        return null;
    }

}
