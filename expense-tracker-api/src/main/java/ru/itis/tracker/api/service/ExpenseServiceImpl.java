package ru.itis.tracker.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.tracker.api.dto.expense.CreateExpenseRequestDto;
import ru.itis.tracker.api.dto.expense.ExpenseDto;
import ru.itis.tracker.api.dto.expense.ExpensePage;
import ru.itis.tracker.api.dto.expense.UpdateExpenseRequestDto;
import ru.itis.tracker.api.exception.NotFoundException;
import ru.itis.tracker.api.mapper.ExpenseMapper;
import ru.itis.tracker.api.model.Expense;
import ru.itis.tracker.api.repository.ExpenseRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final ExpenseCategoryService expenseCategoryService;
    private final UserService userService;

    @Value(value = "${default.page-size}")
    private int pageSize;

    @Override
    public ExpenseDto save(CreateExpenseRequestDto expenseDto) {
        Expense expense = expenseMapper.toModel(expenseDto);

        expense.setTime(Timestamp.from(Instant.now()));
        expense.setUser(userService.findModelById(expenseDto.getUserId()));
        expense.setCategory(expenseCategoryService.findModelById(expenseDto.getCategoryId()));
//        expense.setCurrency(ccc);

        return expenseMapper.toDto(
                expenseRepository.save(expense)
        );
    }

    @Override
    public ExpenseDto findById(UUID id) {
        return expenseMapper.toDto(
                getOrThrow(id)
        );
    }

    @Override
    public ExpenseDto update(UUID id, UpdateExpenseRequestDto expenseDto) {
        Expense expense = getOrThrow(id);

        if (expenseDto.getAmount() != null) {
            expense.setAmount(expenseDto.getAmount());
        }

        if (expenseDto.getCategoryId() != null) {
            expense.setCategory(expenseCategoryService.findModelById(expenseDto.getCategoryId()));
        }

        if (expenseDto.getCurrencyId() != null) {
//            expense.setCurrency();
        }

        return expenseMapper.toDto(
                expenseRepository.save(expense)
        );
    }

    @Override
    public ExpensePage findAllByUserId(UUID userId, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Expense> page = expenseRepository.getAllByUserId(pageRequest, userId);

        return ExpensePage.builder()
                .elementsTotalCount(page.getNumberOfElements())
                .pagesCount(page.getTotalPages())
                .expenses(expenseMapper.toDtoList(page.getContent()))
                .build();
    }

    private Expense getOrThrow(UUID id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Расхода с id [%s] не существует", id)
                ));
    }
}
