package ru.itis.tracker.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.tracker.api.dto.expense.CreateExpenseCategoryDto;
import ru.itis.tracker.api.dto.expense.ExpenseCategoryDto;
import ru.itis.tracker.api.dto.expense.UpdateExpenseCategoryRequestDto;
import ru.itis.tracker.api.exception.NotFoundException;
import ru.itis.tracker.api.mapper.ExpenseMapper;
import ru.itis.tracker.api.model.ExpenseCategory;
import ru.itis.tracker.api.repository.ExpenseCategoryRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpenseMapper expenseMapper;
    private final UserService userService;

    @Override
    public ExpenseCategoryDto save(CreateExpenseCategoryDto categoryDto) {
        ExpenseCategory category = expenseMapper.toModel(categoryDto);

        category.setUser(userService.findModelById(categoryDto.getUserId()));

        return expenseMapper.toDto(
                expenseCategoryRepository.save(category)
        );
    }

    @Override
    public ExpenseCategoryDto update(UUID id, UpdateExpenseCategoryRequestDto categoryDto) {
        ExpenseCategory category = getOrThrow(id);

        if (categoryDto.getBudget() != null) {
            category.setBudget(categoryDto.getBudget());
        }

        if (categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
        }

        return expenseMapper.toDto(
                expenseCategoryRepository.save(category)
        );
    }

    @Override
    public void delete(UUID id) {
        expenseCategoryRepository.delete(
                getOrThrow(id));
    }

    @Override
    public ExpenseCategoryDto findById(UUID id) {
        return expenseMapper.toDto(
                getOrThrow(id)
        );
    }

    @Override
    public ExpenseCategory findModelById(UUID id) {
        return getOrThrow(id);
    }

    private ExpenseCategory getOrThrow(UUID id) {
        return expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Категории с id [%s] не существует", id)));
    }
}
