package ru.itis.tracker.api.security.util;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.util.Map;

public interface JwtUtil {
    Map<String, String> generateTokens(String subject, String authority, String issuer);

    Map<String, String> parse(String token) throws JWTVerificationException;
}
