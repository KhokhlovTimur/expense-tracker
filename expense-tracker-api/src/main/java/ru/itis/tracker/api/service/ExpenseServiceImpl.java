package ru.itis.tracker.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.tracker.api.dto.CurrencyDto;
import ru.itis.tracker.api.dto.expense.CreateExpenseRequestDto;
import ru.itis.tracker.api.dto.expense.ExpenseDto;
import ru.itis.tracker.api.dto.expense.ExpensePage;
import ru.itis.tracker.api.dto.expense.UpdateExpenseRequestDto;
import ru.itis.tracker.api.exception.BudgetExceedingException;
import ru.itis.tracker.api.exception.NotFoundException;
import ru.itis.tracker.api.mapper.ExpenseMapper;
import ru.itis.tracker.api.model.Currency;
import ru.itis.tracker.api.model.Expense;
import ru.itis.tracker.api.model.ExpenseCategory;
import ru.itis.tracker.api.model.User;
import ru.itis.tracker.api.repository.ExpenseRepository;
import ru.itis.tracker.api.service.currency.CurrencyConverter;
import ru.itis.tracker.api.service.currency.CurrencyService;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final ExpenseCategoryService expenseCategoryService;
    private final UserService userService;
    private final CurrencyService currencyService;
    private final CurrencyConverter currencyConverter;

    @Value(value = "${default.page-size}")
    private int pageSize;

    @Override
    public ExpenseDto save(CreateExpenseRequestDto expenseDto) {
        Expense expense = expenseMapper.toModel(expenseDto);
        expense.setTime(new Timestamp(Instant.now().toEpochMilli()));

        User user = userService.findModelById(expenseDto.getUserId());
        ExpenseCategory category = expenseCategoryService.findModelById(expenseDto.getCategoryId());

        checkBudget(user.getId(), expenseDto, category.getBudget());

        expense.setUser(user);
        user.getExpenses().add(expense);

        expense.setCategory(category);
        expense.setCurrency(currencyService.findModelByCode(expenseDto.getCode()));

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

        checkBudget(expense.getUser().getId(),
                expenseDto,
                expenseCategoryService.findModelById(expenseDto.getCategoryId()).getBudget());

        if (expenseDto.getAmount() != null) {
            expense.setAmount(expenseDto.getAmount());
        }

        if (expenseDto.getCategoryId() != null) {
            expense.setCategory(expenseCategoryService.findModelById(expenseDto.getCategoryId()));
        }

        if (expenseDto.getCode() != null) {
            expense.setCurrency(currencyService.findModelByCode(expenseDto.getCode()));
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

    @Override
    public ExpensePage findAllByUserIdWithCurrencyConvert(UUID userId, int pageNumber, String code) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Expense> page = expenseRepository.getAllByUserId(pageRequest, userId);

        List<ExpenseDto> expenses = page.getContent().stream()
                .map(expenseMapper::toDto)
                .peek(x -> {
                    if (x.getCurrency() != null) {
                        x.setAmount(currencyConverter.convert(x.getCurrency().getCode(), code, x.getAmount()));
                        x.setCurrency(currencyService.findByCode(code));
                    }
                }).toList();

        return ExpensePage.builder()
                .elementsTotalCount(page.getNumberOfElements())
                .pagesCount(page.getTotalPages())
                .expenses(expenses)
                .build();
    }

    private Double getAmount(UUID userId, UUID categoryId, String code) {
        return expenseRepository.getAllByUserId(userId).stream()
                .filter(x -> x.getCategory().getId().equals(categoryId))
                .peek(x -> {
                    if (x.getCurrency() != null) {
                        x.setAmount(currencyConverter.convert(x.getCurrency().getCode(), code, x.getAmount()));
                        x.setCurrency(currencyService.findModelByCode(code));
                    }
                })
                .mapToDouble(Expense::getAmount)
                .reduce(0, Double::sum);
    }

    private void checkBudget(UUID userId, CreateExpenseRequestDto expenseDto, int budget) {
        if (getAmount(userId, expenseDto.getCategoryId(), "RUB") + currencyConverter.convert(expenseDto.getCode(), "RUB", expenseDto.getAmount()) > budget) {
            throw new BudgetExceedingException(String.format("Лимит в [%s] RUB превышен", budget));
        }
    }

    private Expense getOrThrow(UUID id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Расхода с id [%s] не существует", id)
                ));
    }
}
