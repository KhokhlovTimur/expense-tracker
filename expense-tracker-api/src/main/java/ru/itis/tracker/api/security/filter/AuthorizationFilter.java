package ru.itis.tracker.api.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.tracker.api.security.util.AuthenticationUtils;
import ru.itis.tracker.api.security.util.RequestParsingUtil;

import java.io.IOException;

import static ru.itis.tracker.api.security.SecurityConstants.AUTHENTICATION_PATH;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    private final RequestParsingUtil requestParsingUtil;
    private final AuthenticationUtils authenticationUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals(AUTHENTICATION_PATH)) {
            filterChain.doFilter(request, response);
        } else {
            if (requestParsingUtil.hasAuthorizationTokenInHeader(request)) {
                String token = requestParsingUtil.getTokenFromHeader(request);

                authenticationUtils.setAuthentication(token, request, response, filterChain);
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

}
