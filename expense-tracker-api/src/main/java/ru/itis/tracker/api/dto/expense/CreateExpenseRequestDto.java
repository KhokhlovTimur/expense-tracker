package ru.itis.tracker.api.dto.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Дто создания расхода")
public class CreateExpenseRequestDto {

    @Schema(description = "Сумма расхода", example = "100", requiredMode = REQUIRED)
    @Positive(message = "{expenseRequest.amount.notPositive}")
    private Double amount;

    @Schema(description = "Id пользователя", requiredMode = REQUIRED)
    private UUID userId;

    @Schema(description = "Id категории расхода", requiredMode = REQUIRED)
    private UUID categoryId;

    @Schema(description = "Id валюты", requiredMode = REQUIRED)
    private UUID currencyId;
}
