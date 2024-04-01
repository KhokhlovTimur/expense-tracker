package ru.itis.tracker.api.mapper;

import org.mapstruct.Mapper;
import ru.itis.tracker.api.dto.CurrencyDto;
import ru.itis.tracker.api.dto.expense.*;
import ru.itis.tracker.api.model.Currency;
import ru.itis.tracker.api.model.Expense;
import ru.itis.tracker.api.model.ExpenseCategory;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ExpenseMapper {

    ExpenseCategory toModel(CreateExpenseCategoryDto categoryDto);

    ExpenseCategoryDto toDto(ExpenseCategory expenseCategory);

    ExpenseCategory toModel(UpdateExpenseCategoryRequestDto categoryRequestDto);

    Expense toModel(CreateExpenseRequestDto createExpenseRequestDto);

    ExpenseDto toDto(Expense expense);

    List<ExpenseDto> toDtoList(List<Expense> expenses);

    List<ExpenseCategoryDto> toCategoryDtoList(List<ExpenseCategory> expenses);

    //todo убрать
    CurrencyDto map(Currency value);

}
