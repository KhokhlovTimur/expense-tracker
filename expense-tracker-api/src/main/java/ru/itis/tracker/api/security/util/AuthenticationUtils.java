package ru.itis.tracker.api.security.util;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface AuthenticationUtils {

    Authentication buildAuthentication(String token) throws JWTVerificationException;

    void setAuthentication(String token, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException;

}
