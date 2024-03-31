package ru.itis.tracker.api.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.tracker.api.repository.tokens.TokenRepository;
import ru.itis.tracker.api.security.util.RequestParsingUtil;

import java.io.IOException;

import static ru.itis.tracker.api.security.SecurityConstants.LOGOUT_PATH;


@Component
@RequiredArgsConstructor
@Slf4j
public class CustomLogoutFilter extends OncePerRequestFilter {

    private final RequestParsingUtil requestParsingUtil;
    private final TokenRepository tokensRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws  IOException, ServletException {
        if (request.getServletPath().equals(LOGOUT_PATH)) {

            if (requestParsingUtil.hasAuthorizationTokenInHeader(request)) {
                log.info("Someone try to logout");
                String token = requestParsingUtil.getTokenFromHeader(request);
                tokensRepository.addAccessToken(token);
                SecurityContextHolder.clearContext();
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
