package ru.itis.tracker.api.aspect.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.itis.tracker.api.dto.ExceptionDto;
import ru.itis.tracker.api.exception.*;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException e) {
        return createResponse(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ExceptionDto> handleAlreadyExistsException(AlreadyExistsException e) {
        return createResponse(HttpStatus.CONFLICT, e);
    }

    @ExceptionHandler(BudgetExceedingException.class)
    public ResponseEntity<ExceptionDto> handleBudgetExceedingException(BudgetExceedingException e) {
        return createResponse(HttpStatus.CONFLICT, e);
    }

    @ExceptionHandler(BankApiException.class)
    public ResponseEntity<ExceptionDto> handleBankApiException(BankApiException e) {
        return createResponse(HttpStatus.SERVICE_UNAVAILABLE, e);
    }

    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<ExceptionDto> handleNoAccessException(NoAccessException e) {
        return createResponse(HttpStatus.FORBIDDEN, e);
    }

    private ResponseEntity<ExceptionDto> createResponse(HttpStatus status, Exception e) {
        log.error(e.getMessage());

        return ResponseEntity.status(status)
                .body(ExceptionDto.builder()
                        .message(e.getMessage())
                        .build());
    }
}
