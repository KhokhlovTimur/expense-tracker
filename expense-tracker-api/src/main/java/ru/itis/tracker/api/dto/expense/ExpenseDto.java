package ru.itis.tracker.api.dto.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.itis.tracker.api.dto.CurrencyDto;
import ru.itis.tracker.api.dto.user.UserDto;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Информация о расходе")
public class ExpenseDto {

    @Schema(description = "Id расхода")
    private UUID id;

    @Schema(description = "Сумма расхода", example = "100")
    @Positive(message = "{expense.amount.notPositive}")
    private Double amount;

    @Schema(description = "Пользователь")
    private UserDto user;

    @Schema(description = "Категория расхода")
    private ExpenseCategoryDto category;

    @Schema(description = "Валюта")
    private CurrencyDto currency;

}
