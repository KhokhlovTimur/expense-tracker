package ru.itis.tracker.api.security.exception;

import org.springframework.security.core.AuthenticationException;

public class RefreshTokenException extends AuthenticationException {
    public RefreshTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
