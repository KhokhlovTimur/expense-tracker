package ru.itis.tracker.api.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import ru.itis.tracker.api.repository.tokens.TokenRepository;
import ru.itis.tracker.api.security.authentication.RefreshTokenAuthentication;
import ru.itis.tracker.api.security.details.UserDetailsImpl;
import ru.itis.tracker.api.security.util.AuthenticationUtils;
import ru.itis.tracker.api.security.util.JwtUtil;
import ru.itis.tracker.api.security.util.RequestParsingUtil;

import java.io.IOException;
import java.util.Map;

import static ru.itis.tracker.api.security.SecurityConstants.USERNAME_PARAMETER;

@Slf4j
@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final RequestParsingUtil requestParsingUtil;
    private final JwtUtil jwtUtil;
    private final TokenRepository tokensRepository;
    private final ObjectMapper objectMapper;

    public AuthenticationFilter(AuthenticationConfiguration authenticationConfiguration,
                                RequestParsingUtil requestParsingUtil,
                                JwtUtil jwtUtil,
                                TokenRepository tokensRepository,
                                ObjectMapper objectMapper) throws Exception {
        super(authenticationConfiguration.getAuthenticationManager());
        this.requestParsingUtil = requestParsingUtil;
        this.setUsernameParameter(USERNAME_PARAMETER);
        this.jwtUtil = jwtUtil;
        this.tokensRepository = tokensRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "*");
        if (requestParsingUtil.hasAuthorizationTokenInHeader(request)) {
            String refreshToken = requestParsingUtil.getTokenFromHeader(request);

            RefreshTokenAuthentication authentication = new RefreshTokenAuthentication(refreshToken);

            return super.getAuthenticationManager().authenticate(authentication);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException,  IOException {
        response.setContentType("application/json");

        GrantedAuthority grantedAuthority = authResult.getAuthorities().stream().findFirst().orElseThrow();
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        Map<String, String> tokens = jwtUtil.generateTokens(
                userDetails.getUsername(), grantedAuthority.toString(), request.getRequestURI()
        );

        //Добавляем рефреш токен в репозиторий после успешной аутентификации
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:3001");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "*");
        tokensRepository.addRefreshToken(tokens.get("refreshToken"));
        objectMapper.writeValue(response.getWriter(), tokens);
    }


}
