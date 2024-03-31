package ru.itis.tracker.api.exception;

public class NoAccessException extends RuntimeException{
    public NoAccessException(String message) {
        super(message);
    }
}
