package ru.itis.tracker.api.dto.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(description = "Дто обновления категории расходов")
public class UpdateExpenseCategoryRequestDto {

    @Schema(description = "Название категории", example = "Еда", requiredMode = REQUIRED)
    @Size(min = 1, max = 50, message = "{updateExpenseCategory.name.size}")
    private String name;

    @Schema(description = "Ежемесячный бюджет в рублях", example = "1000")
    @Positive(message = "{updateExpenseCategory.budget.notPositive}")
    private Integer budget;

}
