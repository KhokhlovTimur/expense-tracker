package ru.itis.tracker.api.mapper;

import org.mapstruct.Mapper;
import ru.itis.tracker.api.dto.CurrencyDto;
import ru.itis.tracker.api.dto.expense.*;
import ru.itis.tracker.api.model.Currency;
import ru.itis.tracker.api.model.Expense;
import ru.itis.tracker.api.model.ExpenseCategory;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ExpenseMapper {

    ExpenseCategory toModel(CreateExpenseCategoryDto categoryDto);

    ExpenseCategoryDto toDto(ExpenseCategory expenseCategory);

    ExpenseCategory toModel(UpdateExpenseCategoryRequestDto categoryRequestDto);

    Expense toModel(CreateExpenseRequestDto createExpenseRequestDto);

    ExpenseDto toDto(Expense expense);

    //todo убрать
    CurrencyDto map(Currency value);

}
