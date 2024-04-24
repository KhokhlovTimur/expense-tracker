package ru.itis.tracker.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tracker.api.controller.api.ExpenseApi;
import ru.itis.tracker.api.dto.expense.*;
import ru.itis.tracker.api.service.ExpenseService;
import ru.itis.tracker.api.service.ExpenseServiceImpl;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ExpenseController implements ExpenseApi {

    private final ExpenseService expenseService;
    private final ExpenseServiceImpl expenseServiceImpl;

    @Override
    public ResponseEntity<ExpenseDto> add(CreateExpenseRequestDto expenseDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(expenseService.save(expenseDto));
    }

    @Override
    public ResponseEntity<ExpenseDto> findById(UUID id) {
        return ResponseEntity.ok(
                expenseService.findById(id)
        );
    }

    @Override
    public ResponseEntity<ExpenseDto> updateFully(UUID id, UpdateExpenseRequestDto expenseDto) {
        return ResponseEntity.accepted()
                .body(expenseService.updateFully(id, expenseDto));
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        expenseServiceImpl.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(null);
    }

    @Override
    public ResponseEntity<ExpenseDto> updatePart(UUID id, UpdateExpenseRequestDto expenseDto) {
        return ResponseEntity.accepted()
                .body(expenseService.update(id, expenseDto));
    }

    @Override
    public ResponseEntity<ExpensePage> findAllByUserId(UUID id, int pageNumber) {
        return ResponseEntity.ok(
                expenseService.findAllByUserId(id, pageNumber)
        );
    }

    @Override
    public ResponseEntity<ExpensePage> findAllByUserIdWithConvert(UUID id, int pageNumber, String code) {
        return ResponseEntity.ok(
                expenseService.findAllByUserIdWithCurrencyConvert(id, pageNumber, code)
        );
    }

    @Override
    public ResponseEntity<ExpenseRangeResponseDto> findAllByUserIdBetween(UUID id, ExpenseRangeRequestDto req) {
        return ResponseEntity.ok(
                expenseService.findAllBetween(id, req)
        );
    }
}
