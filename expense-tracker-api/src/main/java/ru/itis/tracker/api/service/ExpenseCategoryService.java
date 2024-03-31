package ru.itis.tracker.api.service;

import ru.itis.tracker.api.dto.expense.CreateExpenseCategoryDto;
import ru.itis.tracker.api.dto.expense.ExpenseCategoryDto;
import ru.itis.tracker.api.dto.expense.UpdateExpenseCategoryRequestDto;
import ru.itis.tracker.api.model.ExpenseCategory;

import java.util.UUID;

public interface ExpenseCategoryService {

    ExpenseCategoryDto save(CreateExpenseCategoryDto categoryDto);

    ExpenseCategoryDto findById(UUID id);

    ExpenseCategory findModelById(UUID id);

    ExpenseCategoryDto update(UUID id, UpdateExpenseCategoryRequestDto categoryDto);

    void delete(UUID id);

}
