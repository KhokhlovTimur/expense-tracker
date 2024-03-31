package ru.itis.tracker.api.mapper;

import org.mapstruct.Mapper;
import ru.itis.tracker.api.dto.expense.CreateExpenseCategoryDto;
import ru.itis.tracker.api.dto.expense.ExpenseCategoryDto;
import ru.itis.tracker.api.dto.expense.UpdateExpenseCategoryRequestDto;
import ru.itis.tracker.api.model.ExpenseCategory;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ExpenseMapper {

    ExpenseCategory toModel(CreateExpenseCategoryDto categoryDto);

    ExpenseCategoryDto toDto(ExpenseCategory expenseCategory);

    ExpenseCategory toModel(UpdateExpenseCategoryRequestDto categoryRequestDto);

}
