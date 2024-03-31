package ru.itis.tracker.api.dto.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(description = "Дто создания категории расходов")
public class CreateExpenseCategoryDto
{
    @Schema(description = "Id пользователя", requiredMode = REQUIRED)
    private UUID userId;

    @Schema(description = "Название категории", example = "Еда", requiredMode = REQUIRED)
    @Size(min = 1, max = 50)
    private String name;

    @Schema(description = "Ежемесячный бюджет в рублях", example = "1000")
    @Positive
    private Integer budget;
}
