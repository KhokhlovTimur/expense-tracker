package ru.itis.tracker.api.dto.expense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ExpenseRangeResponseDto {
    private List<ExpenseDto> expenses;
}
