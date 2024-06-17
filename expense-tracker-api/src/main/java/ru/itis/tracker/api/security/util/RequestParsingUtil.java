package ru.itis.tracker.api.security.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface RequestParsingUtil {


    boolean hasAuthorizationTokenInHeader(HttpServletRequest request);

    Map<String, String> getDataFromToken(String rawToken);

    String getTokenFromHeader(HttpServletRequest request);

}
