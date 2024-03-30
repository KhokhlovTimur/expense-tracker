package ru.itis.tracker.api.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static ru.itis.tracker.api.security.SecurityConstants.ROLE_PARAMETER;
import static ru.itis.tracker.api.security.SecurityConstants.USERNAME_PARAMETER;

@Component
@RequiredArgsConstructor
public class JwtUtilImpl implements JwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.access-token.expires-time}")
    private long ACCESS_TOKEN_EXPIRES_TIME;
    @Value("${jwt.refresh-token.expires-time}")
    private long REFRESH_TOKEN_EXPIRES_TIME;

    @Override
    public Map<String, String> generateTokens(String subject, String authority, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));

        String accessToken = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRES_TIME))
                .withClaim(ROLE_PARAMETER, authority)
                .withIssuer(issuer)
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRES_TIME))
                .withClaim(ROLE_PARAMETER, authority)
                .withIssuer(issuer)
                .sign(algorithm);

        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    public Map<String, String> parse(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));

        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        Map<String, String> data = new HashMap<>();

        data.put(USERNAME_PARAMETER, decodedJWT.getSubject());
        data.put(ROLE_PARAMETER, decodedJWT.getClaim(ROLE_PARAMETER).asString());

        return data;
    }
}
