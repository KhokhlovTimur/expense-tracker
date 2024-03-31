package ru.itis.tracker.api.service;

import ru.itis.tracker.api.dto.expense.CreateExpenseRequestDto;
import ru.itis.tracker.api.dto.expense.ExpenseDto;
import ru.itis.tracker.api.dto.expense.UpdateExpenseRequestDto;

import java.util.UUID;

public interface ExpenseService {

    ExpenseDto save(CreateExpenseRequestDto expenseDto);

    ExpenseDto findById(UUID id);

    ExpenseDto update(UUID id, UpdateExpenseRequestDto expenseDto);

}
