package ru.itis.tracker.api.service;

import ru.itis.tracker.api.dto.expense.*;

import java.util.UUID;

public interface ExpenseService {

    ExpenseDto save(CreateExpenseRequestDto expenseDto);

    ExpenseDto findById(UUID id);

    ExpenseDto update(UUID id, UpdateExpenseRequestDto expenseDto);

    ExpensePage findAllByUserId(UUID userId, int pageNumber);

    ExpensePage findAllByUserIdWithCurrencyConvert(UUID userId, int pageNumber, String code);

    ExpenseRangeResponseDto findAllBetween(UUID userId, ExpenseRangeRequestDto req);

}
