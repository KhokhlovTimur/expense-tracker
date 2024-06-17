package ru.itis.tracker.api.exception;

public class BankApiException extends RuntimeException {
    public BankApiException(String message) {
        super(message);
    }
}
