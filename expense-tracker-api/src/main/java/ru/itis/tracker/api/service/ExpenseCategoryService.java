package ru.itis.tracker.api.service;

import ru.itis.tracker.api.dto.expense.CreateExpenseCategoryDto;
import ru.itis.tracker.api.dto.expense.ExpenseCategoryDto;
import ru.itis.tracker.api.dto.expense.UpdateExpenseCategoryRequestDto;

import java.util.UUID;

public interface ExpenseCategoryService {

    ExpenseCategoryDto save(CreateExpenseCategoryDto categoryDto);

    ExpenseCategoryDto findById(UUID id);

    ExpenseCategoryDto update(UUID id, UpdateExpenseCategoryRequestDto categoryDto);

    void delete(UUID id);

}
