package ru.itis.tracker.api.security.util;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.tracker.api.exception.NoAccessException;

import java.util.Map;

import static org.apache.tomcat.websocket.Constants.AUTHORIZATION_HEADER_NAME;
import static ru.itis.tracker.api.security.SecurityConstants.BEARER;

@Component
@RequiredArgsConstructor
public class RequestParsingUtilImpl implements RequestParsingUtil {

    public static final String AUTHORIZATION_COOKIE = "access_token";
    private final JwtUtil jwtUtil;

    @Override
    public boolean hasAuthorizationTokenInHeader(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER_NAME);
        return header != null && header.startsWith(BEARER);
    }

    @Override
    public String getTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        return authorizationHeader.substring(BEARER.length());
    }

    @Override
    public Map<String, String> getDataFromToken(String rawToken) {
        if (rawToken.startsWith(BEARER)) {
            try {
                return jwtUtil.parse(rawToken.substring(BEARER.length()));
            } catch (JWTVerificationException e) {
                throw new NoAccessException("No access to the resource");
            }
        } else {
            throw new NoAccessException("No access to the resource");
        }
    }

}
