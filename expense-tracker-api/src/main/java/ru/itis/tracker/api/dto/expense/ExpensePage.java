package ru.itis.tracker.api.dto.expense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ExpensePage {

    private List<ExpenseDto> expenses;
    private int pagesCount;
    private int elementsTotalCount;

}
