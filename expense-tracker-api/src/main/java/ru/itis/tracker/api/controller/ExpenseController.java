package ru.itis.tracker.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tracker.api.controller.api.ExpenseApi;
import ru.itis.tracker.api.dto.expense.CreateExpenseRequestDto;
import ru.itis.tracker.api.dto.expense.ExpenseDto;
import ru.itis.tracker.api.dto.expense.ExpensePage;
import ru.itis.tracker.api.dto.expense.UpdateExpenseRequestDto;
import ru.itis.tracker.api.service.ExpenseService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ExpenseController implements ExpenseApi {

    private final ExpenseService expenseService;

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
    public ResponseEntity<ExpenseDto> update(UUID id, UpdateExpenseRequestDto expenseDto) {
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
}
