package ru.itis.tracker.api.dto.expense;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.itis.tracker.api.dto.user.UserDto;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Информация о категории расходов")
public class ExpenseCategoryDto {

    @Schema(name = "Id категории")
    private UUID id;

    @Schema(description = "Название категории", example = "Еда", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "Бюджет в месяц")
    private int budget;

    @Schema(description = "Пользователь", requiredMode = REQUIRED)
    private UserDto user;

}
