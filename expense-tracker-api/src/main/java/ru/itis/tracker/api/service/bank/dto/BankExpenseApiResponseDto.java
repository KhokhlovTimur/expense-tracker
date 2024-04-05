package ru.itis.tracker.api.service.bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Расход из банка")
public class BankExpenseApiResponseDto {

    @Schema(description = "Id расхода")
    private UUID id;

    @Schema(description = "Сумма расхода", example = "100")
    @Positive(message = "{expense.amount.notPositive}")
    private Double amount;

    @Schema(description = "Время покупки")
    private Timestamp time;

    @Schema(description = "Краткое описание")
    private String description;
}
