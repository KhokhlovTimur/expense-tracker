package ru.itis.tracker.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tracker.api.controller.api.ExpenseCategoryApi;
import ru.itis.tracker.api.dto.expense.CreateExpenseCategoryDto;
import ru.itis.tracker.api.dto.expense.ExpenseCategoryDto;
import ru.itis.tracker.api.dto.expense.ExpenseCategoryPage;
import ru.itis.tracker.api.dto.expense.UpdateExpenseCategoryRequestDto;
import ru.itis.tracker.api.service.ExpenseCategoryService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3001")
public class ExpenseCategoryController implements ExpenseCategoryApi {

    private final ExpenseCategoryService expenseCategoryService;

    @Override
    public ResponseEntity<ExpenseCategoryDto> update(UUID id, UpdateExpenseCategoryRequestDto categoryDto) {
        return ResponseEntity.accepted()
                .body(expenseCategoryService.update(id, categoryDto));
    }

    @Override
    public ResponseEntity<ExpenseCategoryDto> findById(UUID id) {
        return ResponseEntity.ok(
                expenseCategoryService.findById(id));
    }

    @Override
    public ResponseEntity<ExpenseCategoryDto> add(CreateExpenseCategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(expenseCategoryService.save(categoryDto));
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        expenseCategoryService.delete(id);

        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<ExpenseCategoryPage> findAllByUserId(UUID id, int pageNumber) {
        return ResponseEntity.ok(
                expenseCategoryService.findAllByUserId(id, pageNumber)
        );
    }
}
