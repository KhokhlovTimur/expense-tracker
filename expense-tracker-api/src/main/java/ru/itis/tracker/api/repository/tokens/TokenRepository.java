package ru.itis.tracker.api.repository.tokens;

import java.util.Set;

public interface TokenRepository {

    void addAccessToken(String token);

    void addRefreshToken(String token);

    boolean isAccessTokenInBlackList(String token);

    boolean isRefreshTokenExists(String token);

    Set<String> getAll();
}
