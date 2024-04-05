package ru.itis.tracker.api.exception;

public class BudgetExceedingException extends RuntimeException {
    public BudgetExceedingException(String message) {
        super(message);
    }
}
