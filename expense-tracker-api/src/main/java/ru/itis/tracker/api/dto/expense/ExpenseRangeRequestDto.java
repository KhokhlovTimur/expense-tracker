package ru.itis.tracker.api.dto.expense;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Дто запроса для получения расходов в промежутке времени")
public class ExpenseRangeRequestDto {

    @Schema(description = "Начиная с какой даты", example = "2024-04-05")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "yyyy-MM-dd")
    private String from;

    @Schema(description = "До какой даты", example = "2024-12-05")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "yyyy-MM-dd")
    private String to;

}
